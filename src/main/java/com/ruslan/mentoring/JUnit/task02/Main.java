package com.ruslan.mentoring.JUnit.task02;

public class Main {
    public static void main(String[] args) {
        String expression;
        if (args.length == 1) {
            expression = args[0];
        } else {
            expression = "( 1 + 2 ) / ( 10 + 3 * 4 ) ^ 2 / 5 * 3";
        }

        System.out.println(String.format("Expression: %s", expression));
        System.out.println(String.format("Evaluation result: %f", new ExpressionParser(expression).getResult()));
    }
}
