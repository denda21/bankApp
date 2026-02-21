package com.bank_project.service;

import com.bank_project.vo.User;
import com.bank_project.mapper.UserMapper;
import com.bank_project.error.RegisterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.LoginException;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User register(String user_name, String email, String password) throws RegisterException {

        registerValidation(email, password);

        User new_user = new User();
        new_user.setUser_name(user_name);
        new_user.setPassword(passwordEncoder.encode(password));
        new_user.setEmail(email);

        userMapper.insertUser(new_user);

        return new_user;
    }

    @Transactional
    public User login(String email, String password) throws LoginException {

        User user = Optional.ofNullable(userMapper.findUserByEmail(email))
                .orElseThrow(() -> new LoginException("Invalid Email or Password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new LoginException("Invalid Email or Password");
        }

        return user;
    }

    private void registerValidation(String email, String password) throws RegisterException{
        if(email == null || email.isBlank() || email.isEmpty()){
            throw new RegisterException("Invalid email");
        }
        if(password == null || password.isBlank() || password.isEmpty()){
            throw new RegisterException("Invalid password");
        }
        if(password.length()<6){
            throw new RegisterException("password should over 6 length");
        }
        User check = userMapper.findUserByEmail(email);
        if(check != null){
            throw new RegisterException("Email Already exist");
        }
    }

}
