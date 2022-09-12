package com.lab1.exceptions;

public class ModelPriceOutOfBoundsException extends RuntimeException{

    public ModelPriceOutOfBoundsException() {
        super("The price is below zero");
    }
}
