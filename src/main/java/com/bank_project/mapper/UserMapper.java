package com.bank_project.mapper;

import com.bank_project.vo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User findUserByEmail(String email);
    void insertUser(User user);

}