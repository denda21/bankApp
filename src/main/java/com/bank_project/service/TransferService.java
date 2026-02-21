package com.bank_project.service;

import com.bank_project.vo.*;
import com.bank_project.vo.dto.TransferDTO;
import com.bank_project.mapper.AccountMapper;
import com.bank_project.mapper.TransferMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class TransferService {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    private static final Logger log = LoggerFactory.getLogger(TransferService.class);

    public synchronized void sendMoney(TransferDTO transferDTO) throws Exception {

        log.info("Start Sending");
        long start_time = System.currentTimeMillis();

        SqlSession sqlSession = null;

        try {

            sqlSession = sqlSessionFactory.openSession(false);

            AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
            TransferMapper transferMapper = sqlSession.getMapper(TransferMapper.class);

            Account sender = accountMapper.findAccountByNumber(transferDTO.getSender_account_number());
            validation(sender);
            Account receiver = accountMapper.findAccountByNumber(transferDTO.getReceiver_account_number());
            validation(receiver);

            if (sender.getAccount_number().equals(receiver.getAccount_number())) {
                throw new RuntimeException("Same Account, Please try again");
            }

            if (sender.getBalance().compareTo(transferDTO.getAmount()) < 0) {
                throw new Exception("Not enough money on your Account");
            }

            sender.setBalance(sender.getBalance().subtract(transferDTO.getAmount()));
            receiver.setBalance(receiver.getBalance().add(transferDTO.getAmount()));

            accountMapper.updateBalance(sender);
            accountMapper.updateBalance(receiver);

            Transfer transfer = new Transfer();
            transfer.setSender_id(sender.getAccount_id());
            transfer.setReceiver_id(receiver.getAccount_id());
            transfer.setAmount(transferDTO.getAmount());
            transfer.setTransfer_time(LocalDateTime.now());
            transferMapper.insertTransfer(transfer);

            // transfer history
            List<TransferHistory> history_list = List.of(
                    new TransferHistory(sender.getAccount_id(), receiver.getAccount_name(), transferDTO.getAmount(),
                            "OUT"),
                    new TransferHistory(receiver.getAccount_id(), receiver.getAccount_name(), transferDTO.getAmount(),
                            "IN"));

            transferMapper.insertHistory(history_list);

            sqlSession.commit();

            long result_time = System.currentTimeMillis() - start_time;
            log.info("Sending money finished : {}ms", result_time);

        } catch (Exception e) {
            if (sqlSession != null) {
                sqlSession.rollback();
            }
            log.error("Sending Money Error", e);
            throw e;
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    // Pessimistic Lock
    public void sendMoneyWithDBLock(TransferDTO transferDTO) throws Exception {

        log.info("Start Sending with Pessimistic Lock");
        long start_time = System.currentTimeMillis();
        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession(false);
            AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
            TransferMapper transferMapper = sqlSession.getMapper(TransferMapper.class);

            // To prevent deadlock (order by ID)
            Account sender, receiver;
            int sender_id = accountMapper.findIDByNumber(transferDTO.getSender_account_number());
            int receiver_id = accountMapper.findIDByNumber(transferDTO.getReceiver_account_number());

            if (sender_id < receiver_id) {
                sender = accountMapper.findForUpdate(sender_id);
                validation(sender);
                receiver = accountMapper.findForUpdate(receiver_id);
                validation(receiver);
            } else {
                receiver = accountMapper.findForUpdate(receiver_id);
                validation(receiver);
                sender = accountMapper.findForUpdate(sender_id);
                validation(sender);
            }

            if (sender.getAccount_number().equals(receiver.getAccount_number())) {

                throw new RuntimeException("Same Account, Please try again");
            }

            if (sender.getBalance().compareTo(transferDTO.getAmount()) < 0) {
                throw new Exception("Not enough money on your Account");
            }

            sender.setBalance(sender.getBalance().subtract(transferDTO.getAmount()));
            receiver.setBalance(receiver.getBalance().add(transferDTO.getAmount()));

            accountMapper.updateBalance(sender);
            accountMapper.updateBalance(receiver);

            Transfer transfer = new Transfer();
            transfer.setSender_id(sender.getAccount_id());
            transfer.setReceiver_id(receiver.getAccount_id());
            transfer.setAmount(transferDTO.getAmount());
            transfer.setTransfer_time(LocalDateTime.now());
            transferMapper.insertTransfer(transfer);

            // transfer history
            List<TransferHistory> history_list = List.of(
                    new TransferHistory(sender.getAccount_id(), receiver.getAccount_name(), transferDTO.getAmount(),
                            "OUT"),
                    new TransferHistory(receiver.getAccount_id(), receiver.getAccount_name(), transferDTO.getAmount(),
                            "IN"));

            transferMapper.insertHistory(history_list);

            sqlSession.commit();

            long result_time = System.currentTimeMillis() - start_time;
            log.info("Sending money finished : {}ms", result_time);

        } catch (Exception e) {
            if (sqlSession != null) {
                sqlSession.rollback();
            }
            log.error("Sending Money Error", e);
            throw e;
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    private void validation(Account account) {
        if (account == null)
            throw new RuntimeException("Transfer Account Validation Error");
    }

}
