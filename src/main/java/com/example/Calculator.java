package com.example;

public class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public double divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return (double) a / b;
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        System.out.println("Addition: 5 + 3 = " + calc.add(5, 3));
        System.out.println("Subtraction: 5 - 3 = " + calc.subtract(5, 3));
        System.out.println("Multiplication: 5 * 3 = " + calc.multiply(5, 3));
        System.out.println("Division: 10 / 2 = " + calc.divide(10, 2));
    }
}
