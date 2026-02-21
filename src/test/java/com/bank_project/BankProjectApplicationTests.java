package com.bank_project;

import com.bank_project.service.*;
import com.bank_project.vo.*;
import com.bank_project.vo.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BankProjectApplicationTests {

	@Autowired
	private AuthService authService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private TransferService transferService;

	@Test
	void testUserCreateAndTransfer() throws Exception {

		User user1 = authService.register("test1", "test1@test.com", "12345678");
		User user2 = authService.register("test2", "test2@test.com", "testpassword");

		Account account1 = accountService.createAccountWithBalance(user1.getUser_id(), "test1's account1",
				new BigDecimal("90000"));
		Account account2 = accountService.createAccountWithBalance(user2.getUser_id(), "test2's account2",
				new BigDecimal("10000"));

		int threadCount = 100;
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);

		int transferCount = 500;
		CountDownLatch latch = new CountDownLatch(transferCount);

		long startTime = System.currentTimeMillis();

		for (int i = 0; i < transferCount; i++) {
			executor.submit(() -> {
				try {
					TransferDTO transferDTO = new TransferDTO();
					transferDTO.setSender_account_number(account1.getAccount_number());
					transferDTO.setReceiver_account_number(account2.getAccount_number());
					transferDTO.setAmount(new BigDecimal("100"));
					try {
						transferService.sendMoney(transferDTO);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		executor.shutdown();

		long result = System.currentTimeMillis() - startTime;
		System.out.printf("result time : %d ms\n", result);

		Account updatedAccount1 = accountService.findAccountByNumber(account1.getAccount_number());
		Account updatedAccount2 = accountService.findAccountByNumber(account2.getAccount_number());

		assertThat(updatedAccount1.getBalance()).isEqualTo(new BigDecimal("40000"));
		assertThat(updatedAccount2.getBalance()).isEqualTo(new BigDecimal("60000"));
	}

	@Test
	void testSendMoneyWithDBLock() throws Exception {

		User user1 = authService.register("test3", "test3@test.com", "12345678");
		User user2 = authService.register("test4", "test4@test.com", "testpassword");

		Account account1 = accountService.createAccountWithBalance(user1.getUser_id(), "test3's account1",
				new BigDecimal("90000"));
		Account account2 = accountService.createAccountWithBalance(user2.getUser_id(), "test4's account2",
				new BigDecimal("10000"));

		int threadCount = 100;
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);

		int transferCount = 500;
		CountDownLatch latch = new CountDownLatch(transferCount);

		long startTime = System.currentTimeMillis();

		for (int i = 0; i < transferCount; i++) {
			executor.submit(() -> {
				try {
					TransferDTO transferDTO = new TransferDTO();
					transferDTO.setSender_account_number(account1.getAccount_number());
					transferDTO.setReceiver_account_number(account2.getAccount_number());
					transferDTO.setAmount(new BigDecimal("100"));
					try {
						transferService.sendMoneyWithDBLock(transferDTO);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		executor.shutdown();

		long result = System.currentTimeMillis() - startTime;
		System.out.printf("result time : %d ms\n", result);

		Account updatedAccount1 = accountService.findAccountByID(account1.getAccount_id());
		Account updatedAccount2 = accountService.findAccountByID(account2.getAccount_id());

		assertThat(updatedAccount1.getBalance()).isEqualTo(new BigDecimal("40000"));
		assertThat(updatedAccount2.getBalance()).isEqualTo(new BigDecimal("60000"));
	}

	@Test
	public void generateHash() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String hash = encoder.encode("123456");
		System.out.println("Bcrypt Hash=" + hash);
	}
}
