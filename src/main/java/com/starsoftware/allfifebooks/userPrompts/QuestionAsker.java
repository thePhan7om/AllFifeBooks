package com.starsoftware.allfifebooks.userPrompts;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Jordan
 * Date: 28/09/2013
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */
public class QuestionAsker {

    private final Scanner scanner;
    private final PrintStream out;

    public QuestionAsker(InputStream in, PrintStream out) {
        scanner = new Scanner(in);
        this.out = out;
    }

    public String ask(String message) {
        out.println(message);
        return scanner.nextLine();
    }
}

