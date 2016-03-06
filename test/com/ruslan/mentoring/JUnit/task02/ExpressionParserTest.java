package com.ruslan.mentoring.JUnit.task02;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExpressionParserTest {
    @Test
    public void test() {
        ExpressionParser parser = new ExpressionParser("( 1 + 2 ) / ( 10 + 3 * 4 ) ^ 2 / 5 * 3");
        assertEquals("1 2 + 10 3 4 * + 2 ^ / 5 / 3 * ", parser.buildPostfix());
        assertEquals(0.003719, parser.getResult(), 0.00000001);
    }
}