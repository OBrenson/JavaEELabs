package com.lab1.exceptions;

public class NoSuchModelNameException extends RuntimeException {

    public NoSuchModelNameException(String name) {
        super(String.format("No model with name: %s", name));
    }

}
