package com.elotech.process.crud.model.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super("Entity not found");
    }
}
