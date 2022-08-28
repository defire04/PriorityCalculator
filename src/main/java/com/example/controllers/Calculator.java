package com.example.controllers;


import java.util.Stack;

public class Calculator {

    public static double preparingExpression(String expression) {
        StringBuilder prepareExpression = new StringBuilder();

        String[] expressionSymbols = expression.split("");

        for (int i = 0; i < expressionSymbols.length; i++) {


            if (expressionSymbols[i].equals("-")) {
                if (i == 0) {
                    prepareExpression.append("0");
                } else if (expressionSymbols[i - 1].equals("+") || expressionSymbols[i - 1].equals("(")) {
                    prepareExpression.append("0");
                } else if (expressionSymbols[i - 1].equals("-")) {
                    prepareExpression.replace(prepareExpression.length() - 1, prepareExpression.length(), "+");
                    continue;
                } else if (expressionSymbols[i - 1].equals("*") || expressionSymbols[i - 1].equals("/")) {
                    System.out.println("prr" + prepareExpression);

                    prepareExpression.replace(prepareExpression.length() - 2, prepareExpression.length(), "0-"
                            + prepareExpression.toString().charAt(prepareExpression.length() - 2) + prepareExpression.toString().charAt(prepareExpression.length() - 1));
                    continue;
                }
            }
            prepareExpression.append(expression.charAt(i));
        }
        System.out.println(prepareExpression);
        return expressionToRPN(prepareExpression.toString());
    }

    private static double expressionToRPN(String expression) { //  Reverse Polish Notation
        StringBuilder current = new StringBuilder();
        Stack<String> stack = new Stack<>();
        int priority;

        for (String symbol : expression.split("")) {
            priority = getPrioritySign(symbol);

            if (priority == 0) {
                current.append(symbol);
            } else if (priority == 1) {
                stack.push(symbol);
            } else if (priority > 1) {
                current.append(" ");
                while (!stack.empty()) {
                    if (getPrioritySign(stack.peek()) >= priority) {
                        current.append(stack.pop());
                    } else {
                        break;
                    }
                }
                stack.push(symbol);
            } else if (priority == -1) {
                current.append(" ");
                while (getPrioritySign(stack.peek()) != 1) {
                    current.append(stack.pop());
                }
                stack.pop();
            }
        }

        while (!stack.empty()) {
            current.append(stack.pop());
        }
        return RPNToResult(String.valueOf(current));
    }

    private static double RPNToResult(String rpn) {

        StringBuilder operand = new StringBuilder();
        Stack<Double> stack = new Stack<>();

        for (int i = 0; i < rpn.length(); i++) {
            if (rpn.charAt(i) == ' ') {
                continue;
            }
            if (getPrioritySign(String.valueOf(rpn.charAt(i))) == 0) {
                while (rpn.charAt(i) != ' ' && getPrioritySign(String.valueOf(rpn.charAt(i))) == 0) {
                    operand.append(rpn.charAt(i++));
                    if (i == rpn.length()) {
                        break;
                    }
                }

                stack.push(Double.parseDouble(operand.toString()));
                operand = new StringBuilder();

            }

            if (getPrioritySign(String.valueOf(rpn.charAt(i))) > 1) {
                double a = stack.pop();
                double b = stack.pop();

                if (rpn.charAt(i) == '+') {
                    stack.push(b + a);
                } else if (rpn.charAt(i) == '-') {
                    stack.push(b - a);
                } else if (rpn.charAt(i) == '*') {
                    stack.push(b * a);
                } else if (rpn.charAt(i) == '/') {
                    stack.push(b / a);
                }
            }
        }
        return stack.pop();
    }


    private static int getPrioritySign(String symbol) {
        return switch (symbol) {
            case "*", "/" -> 3;
            case "+", "-" -> 2;
            case "(" -> 1;
            case ")" -> -1;
            default -> 0;
        };
    }
}
