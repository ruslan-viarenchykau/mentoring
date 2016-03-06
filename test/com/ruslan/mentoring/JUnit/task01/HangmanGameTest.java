package com.ruslan.mentoring.JUnit.task01;

import org.junit.Test;

import static org.junit.Assert.*;

public class HangmanGameTest {

    @Test
    public void testTakeAttempt_zeroAttempts() throws Exception {
        HangmanGame game = new HangmanGame("123", 0);
        assertFalse(game.takeAttempt('1'));
        assertFalse(game.takeAttempt('2'));
        assertFalse(game.takeAttempt('3'));
        assertFalse(game.takeAttempt('4'));
    }

    @Test
    public void testTakeAttempt_fewAttempts_attemptsOver() throws Exception {
        HangmanGame game = new HangmanGame("123", 2);
        assertFalse(game.takeAttempt('5'));
        assertFalse(game.takeAttempt('4'));
        assertFalse(game.takeAttempt('3'));
        assertFalse(game.takeAttempt('2'));
        assertFalse(game.takeAttempt('1'));
    }

    @Test
    public void testTakeAttempt_fewAttempts() throws Exception {
        HangmanGame game = new HangmanGame("123", 2);
        assertTrue(game.takeAttempt('1'));
        assertTrue(game.takeAttempt('2'));
        assertTrue(game.takeAttempt('3'));
        assertFalse(game.takeAttempt('4'));

        assertFalse(game.takeAttempt('1'));
        assertFalse(game.takeAttempt('2'));
        assertFalse(game.takeAttempt('3'));
        assertFalse(game.takeAttempt('4'));
    }

    @Test
    public void testIsGameOver_zeroAttempts() throws Exception {
        HangmanGame game = new HangmanGame("123", 0);
        assertTrue(game.isGameOver());
    }

    @Test
    public void testIsGameOver_fewAttempts_correctInput() throws Exception {
        HangmanGame game = new HangmanGame("123", 2);
        assertFalse(game.isGameOver());

        game.takeAttempt('1');
        assertFalse(game.isGameOver());
        game.takeAttempt('2');
        assertFalse(game.isGameOver());
        game.takeAttempt('3');
        assertTrue(game.isGameOver());
    }

    @Test
    public void testIsGameOver_fewAttempts_incorrectInput() throws Exception {
        HangmanGame game = new HangmanGame("123", 2);
        assertFalse(game.isGameOver());

        game.takeAttempt('4');
        assertFalse(game.isGameOver());
        game.takeAttempt('5');
        assertTrue(game.isGameOver());
    }

    @Test
    public void testIsWin_justStarted() throws Exception {
        HangmanGame game = new HangmanGame("123", 2);
        assertFalse(game.isWin());
    }

    @Test
    public void testIsWin_attemptsOver() throws Exception {
        HangmanGame game = new HangmanGame("123", 2);
        game.takeAttempt('4');
        assertFalse(game.isWin());
        game.takeAttempt('5');
        assertFalse(game.isWin());
        game.takeAttempt('1');
        assertFalse(game.isWin());
        game.takeAttempt('2');
        assertFalse(game.isWin());
        game.takeAttempt('3');
        assertFalse(game.isWin());
    }

    @Test
    public void testIsWin_afterIncorrectAttempt() throws Exception {
        HangmanGame game = new HangmanGame("123", 2);
        game.takeAttempt('4');
        assertFalse(game.isWin());
        game.takeAttempt('3');
        assertFalse(game.isWin());
        game.takeAttempt('2');
        assertFalse(game.isWin());
        game.takeAttempt('1');
        assertTrue(game.isWin());
        game.takeAttempt('0');
        assertTrue(game.isWin());
    }

    @Test
    public void testIsWin() throws Exception {
        HangmanGame game = new HangmanGame("123", 2);
        game.takeAttempt('1');
        assertFalse(game.isWin());
        game.takeAttempt('2');
        assertFalse(game.isWin());
        game.takeAttempt('3');
        assertTrue(game.isWin());
        game.takeAttempt('4');
        assertTrue(game.isWin());
        game.takeAttempt('5');
        assertTrue(game.isWin());
    }

    @Test
    public void testGetMask() throws Exception {
        HangmanGame game = new HangmanGame("123", 2);
        assertEquals("***", game.getMask());
        game.takeAttempt('0');
        assertEquals("***", game.getMask());
        game.takeAttempt('1');
        assertEquals("1**", game.getMask());
        game.takeAttempt('2');
        assertEquals("12*", game.getMask());
        game.takeAttempt('3');
        assertEquals("123", game.getMask());
        game.takeAttempt('4');
        assertEquals("123", game.getMask());
    }

    @Test
    public void testGetMask_equalCharacters() throws Exception {
        HangmanGame game = new HangmanGame("123321", 2);
        assertEquals("******", game.getMask());

        game.takeAttempt('0');
        assertEquals("******", game.getMask());

        game.takeAttempt('1');
        assertEquals("1*****", game.getMask());

        game.takeAttempt('2');
        assertEquals("12****", game.getMask());

        game.takeAttempt('3');
        assertEquals("123***", game.getMask());

        game.takeAttempt('1');
        assertEquals("123**1", game.getMask());

        game.takeAttempt('2');
        assertEquals("123*21", game.getMask());

        game.takeAttempt('3');
        assertEquals("123321", game.getMask());

        game.takeAttempt('4');
        assertEquals("123321", game.getMask());
    }

    @Test
    public void testGetAttempts() throws Exception {
        HangmanGame game = new HangmanGame("123321", 2);
        assertEquals(2, game.getAttempts());

        game.takeAttempt('0');
        assertEquals(1, game.getAttempts());

        game.takeAttempt('1');
        assertEquals(1, game.getAttempts());

        game.takeAttempt('2');
        assertEquals(1, game.getAttempts());

        game.takeAttempt('3');
        assertEquals(1, game.getAttempts());

        game.takeAttempt('4');
        assertEquals(0, game.getAttempts());
    }

    @Test
    public void testGetWord() throws Exception {
        HangmanGame game = new HangmanGame("123321", 2);
        assertEquals("123321", game.getWord());
    }
}