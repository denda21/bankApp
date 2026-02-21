package com.bank_project.error;

public class InvalidAccountException extends  RuntimeException {

    public InvalidAccountException(String message){
        super(message);
    }
}
