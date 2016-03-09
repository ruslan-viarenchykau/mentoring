package com.ruslan.mentoring.JUnit.task02;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExpressionParserTest {
    private ExpressionParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new ExpressionParser("( 1 + 2 ) / ( 10 + 3 * 4 ) ^ 2 / 5 * 3");
    }

    @Test
    public void testGetResult() throws Exception {
        assertEquals(0.003719, parser.getResult(), 0.00000001);
    }

    @Test
    public void testBuildPostfix() throws Exception {
        assertEquals("1 2 + 10 3 4 * + 2 ^ / 5 / 3 * ", parser.buildPostfix());
        assertEquals("1 2 + 10 3 4 * + 2 ^ / 5 / 3 * ", parser.buildPostfix());
    }

    @Test
    public void testEvaluate() throws Exception {
        assertEquals(0.003719, parser.evaluate("1 2 + 10 3 4 * + 2 ^ / 5 / 3 *"), 0.00000001);
        assertEquals(3.0, parser.evaluate("3"), 0);
    }

    @Test
    public void testOperateSingle() throws Exception {
        assertEquals(Double.valueOf(1.02), parser.operateSingle(1.01, 0.01, ExpressionParser.Operation.ADD));
        assertEquals(Double.valueOf(-1.0), parser.operateSingle(1.01, 0.01, ExpressionParser.Operation.SUB));
        assertEquals(Double.valueOf(0.0101), parser.operateSingle(1.01, 0.01, ExpressionParser.Operation.MUL));
        assertEquals(Double.valueOf(101.0), parser.operateSingle(0.01, 1.01, ExpressionParser.Operation.DIV));
        assertEquals(Double.valueOf(15.625), parser.operateSingle(3.0, 2.5, ExpressionParser.Operation.POW));
    }
}