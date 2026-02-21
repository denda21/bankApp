package com.bank_project.mapper;

import com.bank_project.vo.TransferHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HistoryMapper {

    int countAccountHistory(@Param("account_id") int account_id);
    List<TransferHistory> findHistoryByAccountId(@Param("account_id") int account_id,
                                                 @Param("start_idx") int start_idx,
                                                 @Param("rowPerPage") int rowPerPage);
}