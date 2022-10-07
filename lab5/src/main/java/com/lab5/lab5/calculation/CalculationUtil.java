package com.lab5.lab5.calculation;

public class CalculationUtil {

    private static final Automaton automaton = new Automaton();

    public static Double next(Operation operation) throws CalculationException {
        automaton.nextStep(operation);
        return automaton.first;
    }

    public static void next(Double num) {
        automaton.nextStep(num);
    }

    public static Double changeSign() {
        if (automaton.second == null || automaton.lastEqual) {
            automaton.first = -1 * automaton.first;
            System.out.println(automaton.first);
            return automaton.first;
        }
        System.out.println("null");
        return null;
    }

    public static void clear() {
        automaton.first = 0.0;
    }

    private static class Automaton {
        private Double first;
        private Double second;

        private Operation operation;

        private boolean lastEqual;

        private void nextStep(Operation operation) throws CalculationException {
            if (first == null) {
                return;
            }
            if (operation == Operation.SQRT) {
                this.operation = operation;
                first = executeOperation();
                this.operation = null;
                second = null;
                return;
            }
            if (this.operation == null || second == null) {
                this.operation = operation;
                return;
            }
            if (operation != Operation.EQUAL) {
                if (!lastEqual) {
                    first = executeOperation();
                }
                lastEqual = false;
                this.operation = operation;
                second = null;
            } else {
                first = executeOperation();
                lastEqual = true;
            }
        }

        private void nextStep(Double num) {
            if (first == null || operation == null) {
                first = num;
            } else {
                second = num;
            }
        }

        private Double executeOperation() throws CalculationException {
            switch (operation) {
                case DIV:
                    if (second == 0) {
                        throw new CalculationException("dev by zero");
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
                        throw new CalculationException("sqrt negative num");
                    }
                    return Math.sqrt(first);
                case EXPON:
                    return Math.pow(first, second);
                default:
                    return first;
            }
        }
    }
}
