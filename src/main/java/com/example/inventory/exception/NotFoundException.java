package com.example.inventory.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) {
      super(msg);
    }
}
