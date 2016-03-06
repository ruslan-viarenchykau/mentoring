package com.ruslan.mentoring.JUnit.task02;

import java.util.*;

public class ExpressionParser {
    private String infix;

    public enum Operation {
        ADD, SUB, MUL, DIV, POW
    }

    public static HashMap<String, Operation> operators = new HashMap<String, Operation>(){{
        put("+", Operation.ADD);
        put("-", Operation.SUB);
        put("*", Operation.MUL);
        put("/", Operation.DIV);
        put("^", Operation.POW);
    }};

    public ExpressionParser(String infix) {
        this.infix = infix;
    }

    public double getResult() {
        String postfix = buildPostfix();
        return evaluate(postfix);
    }

    /**
     * Method to parse an infix mathematical expression into Reverse Polish notation (postfix). Using said notation
     * allows the computer to evaluate the expression in a simple stack based form
     */
    protected String buildPostfix() {
        final String ops = "-+/*^";
        StringBuilder sb = new StringBuilder();
        Stack<Integer> s = new Stack<>();

        for (String token : infix.split("\\s")) {
            if (token.isEmpty()) {
                continue;
            }
            char c = token.charAt(0);
            int idx = ops.indexOf(c);

            // check for operator
            if (idx != -1) {
                if (s.isEmpty()) {
                    s.push(idx);
                } else {
                    while (!s.isEmpty()) {
                        int prec2 = s.peek() / 2;
                        int prec1 = idx / 2;
                        if (prec2 > prec1 || (prec2 == prec1 && c != '^'))
                            sb.append(ops.charAt(s.pop())).append(' ');
                        else break;
                    }
                    s.push(idx);
                }
            } else if (c == '(') {
                s.push(-2); // -2 stands for '('
            } else if (c == ')') {
                // until '(' on stack, pop operators.
                while (s.peek() != -2) {
                    sb.append(ops.charAt(s.pop())).append(' ');
                }
                s.pop();
            } else {
                sb.append(token).append(' ');
            }
        }
        while (!s.isEmpty()) {
            sb.append(ops.charAt(s.pop())).append(' ');
        }
        return sb.toString();
    }

    // Evaluate a postfix expression in String, return the evaluated result in Double
    protected Double evaluate(String postfixExpression){
        // operand stack, the core data structure in the algorithm
        Stack<Double> stack = new Stack<>();

        // tokenize the string first
        List<Object> tokens = new LinkedList<>();
        Scanner tokensScanner = new Scanner(postfixExpression);
        String token;
        while (tokensScanner.hasNext()){
            token = tokensScanner.next();
            if (operators.containsKey(token)) {
                tokens.add(operators.get(token));
            } else {
                tokens.add(Double.valueOf(token));
            }
        }

        // process each item in the LinkedList
        for (Object item : tokens) {
            // separate the current item into two categories
            if (item instanceof Operation) {
                /**
                 * for operators, pop the top two operands from the stack,
                 * calculate the result of the single operation, and push
                 * it to the top of the stack
                 */
                stack.push(operateSingle(stack.pop(), stack.pop(), (Operation)item) );
            } else {
                /**
                 * for operands, push it to the stack
                 */
                stack.push((Double)item);
            }
        }
        // the current top item in the stack should be the result
        return stack.pop();
    }

    /**
     * This method simply carry calculation of a specific operation
     */
    private Double operateSingle(Double op2, Double op1, Operation operation) {
        switch(operation) {
            case ADD:
                return op1+op2;
            case SUB:
                return op1-op2;
            case MUL:
                return op1*op2;
            case DIV:
                return op1/op2;
            case POW:
                return (Math.pow(op1, op2));
        }
        return null;
    }
}
