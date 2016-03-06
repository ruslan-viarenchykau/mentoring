package com.ruslan.mentoring.JUnit.task01;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class HangmanGame {
    protected static final String CHARACTER_MASK = "*";

    private String word;
    private StringBuilder mask;
    private int attempts;

    public HangmanGame(String word, int attempts) {
        this.word = word;
        this.mask = new StringBuilder(StringUtils.repeat(CHARACTER_MASK, word.length()));
        this.attempts = attempts;
    }

    public boolean takeAttempt(char character) throws IOException {
        if (!isGameOver()) {
            int maskIndex = mask.lastIndexOf(String.valueOf(character));
            int index = word.indexOf(character, maskIndex + 1);
            if (index > -1) {
                mask.setCharAt(index, character);
                return true;
            } else {
                attempts--;
            }
        }
        return false;
    }

    public boolean isGameOver() {
        return attempts == 0 || isWin();
    }

    public boolean isWin() {
        return word.equals(mask.toString());
    }

    public String getMask() {
        return mask.toString();
    }

    public String getWord() {
        return word;
    }

    public int getAttempts() {
        return attempts;
    }
}
