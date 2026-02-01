package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    public void setUp() {
        calculator = new Calculator();
    }

    @Test
    public void testAddition() {
        assertEquals(8, calculator.add(5, 3));
        assertEquals(0, calculator.add(-5, 5));
    }

    @Test
    public void testSubtraction() {
        assertEquals(2, calculator.subtract(5, 3));
        assertEquals(-10, calculator.subtract(-5, 5));
    }

    @Test
    public void testMultiplication() {
        assertEquals(15, calculator.multiply(5, 3));
        assertEquals(0, calculator.multiply(0, 5));
    }

    @Test
    public void testDivision() {
        assertEquals(2.5, calculator.divide(5, 2));
        assertEquals(5.0, calculator.divide(10, 2));
    }

    @Test
    public void testDivisionByZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(5, 0);
        });
    }
}
