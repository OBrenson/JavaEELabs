package com.lab5.lab5.calculation;

public enum Operation {
    EQUAL("="),
    SUM("+"),
    MINUS("-"),
    MULT("*"),
    SQRT("√"),
    EXPON("^"),
    DIV("/");

    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    public static Operation getOperation(String symbol) {
        for (Operation op : Operation.values()) {
            if (symbol.equals(op.symbol)) {
                return op;
            }
        }
        return null;
    }
}
