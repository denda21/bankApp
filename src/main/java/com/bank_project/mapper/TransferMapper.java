package com.bank_project.mapper;

import com.bank_project.vo.Transfer;
import com.bank_project.vo.TransferHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TransferMapper {

    void insertTransfer(Transfer transfer);
    void insertHistory(List<TransferHistory> transferHistory);

}
