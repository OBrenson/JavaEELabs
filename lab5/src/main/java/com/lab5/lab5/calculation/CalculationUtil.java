package com.lab5.lab5.calculation;

public class CalculationUtil {

    private static final Automaton automaton = new Automaton();

    public static Double next(Operation operation) throws CalculationException {
        automaton.nextStep(operation);
        return automaton.first;
    }

    public static Double next(Double num) throws CalculationException {
        automaton.nextStep(num);
        return automaton.first;
    }

    private static class Automaton {
        private Double first;
        private Double second;

        private Operation operation;

        private Double result;

        private void nextStep(Operation operation) throws CalculationException {
            if (first == null) {
                return;
            }
            if (operation == null || second == null) {
                this.operation = operation;
                return;
            }
            first = executeOperation();
            if (operation != Operation.EQUAL) {
                this.operation = operation;
                second = null;
            }
        }

        private void nextStep(Double num) {
            if (first == null) {
                first = num;
            } else {
                second = num;
            }
        }

        private Double executeOperation() throws CalculationException {
            switch (operation) {
                case DIV:
                    if (second == 0) {
                        throw new CalculationException("Can not dev by zero");
                    }
                    return first/second;
                case SUM:
                    return first + second;
                case MINUS:
                    return first - second;
                case MULT:
                    return first * second;
                case SQRT:
                    if (first < 0) {
                        throw new CalculationException("Can not sqrt negative num");
                    }
                    return Math.sqrt(first);
                case EXPON:
                    return Math.pow(first, second);
            }
            return null;
        }
    }
}
