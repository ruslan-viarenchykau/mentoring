package com.ruslan.mentoring.JUnit.task01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static final String DEFAULT_WORD = "hangman";
    private static final int DEFAULT_ATTEMPTS = 10;

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        HangmanGame hangmanGame = initHangmanGame(args);

        System.out.println("Game started!");
        while (!hangmanGame.isGameOver()) {
            System.out.print(String.format("Attempts: %d, Word: %s. Enter character: ",
                    hangmanGame.getAttempts(), hangmanGame.getMask()));

            hangmanGame.takeAttempt(readChar());
            if (hangmanGame.isWin()) {
                System.out.println(String.format("You win! Word is %s", hangmanGame.getWord()));
                break;
            }
        }
        System.out.println("Game over");
        reader.close();
    }

    private static HangmanGame initHangmanGame(String[] args) {
        String word;
        int attempts;

        int argsNumber = args.length;
        if (argsNumber == 1 || argsNumber ==2) {
            word = args[0];
            if (argsNumber == 2) {
                try {
                    attempts = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid format of the second argument. Default attempts number will be used");
                    attempts = DEFAULT_ATTEMPTS;
                }
            } else {
                attempts = DEFAULT_ATTEMPTS;
            }
        } else {
            word = DEFAULT_WORD;
            attempts = DEFAULT_ATTEMPTS;
            System.out.println("Invalid number of program arguments. Default word and default attempts number will be used");
        }

        return new HangmanGame(word, attempts);
    }

    private static char readChar() throws IOException {
        String inputString = reader.readLine();
        while (inputString.length() != 1) {
            System.out.print("Please enter single character: ");
            inputString = reader.readLine();
        }
        return inputString.charAt(0);
    }
}
