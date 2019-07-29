package com.t_systems.webstore.exception;

public class UserExistsException extends Exception {

    UserExistsException(String message) {
        super(message);
    }

    public UserExistsException() {
        super("User already exists!");
    }
}
