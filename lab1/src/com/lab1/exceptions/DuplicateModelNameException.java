package com.lab1.exceptions;

public class DuplicateModelNameException extends Exception{

    public DuplicateModelNameException(String modelName) {
        super(String.format("Model with name %s is already exists", modelName));
    }
}
