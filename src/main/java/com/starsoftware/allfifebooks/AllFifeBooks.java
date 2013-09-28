package com.starsoftware.allfifebooks;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AllFifeBooks
{
    private static final Logger log = Logger.getLogger(AllFifeBooks.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
        AllFifeBooks allFifeBooks = new AllFifeBooks();
        allFifeBooks.runApplication(new QuestionAsker(System.in, System.out));

    }

    public String runApplication(QuestionAsker questionAsker) {
      String name =  questionAsker.ask("Enter your name: ") ;
        //  open up standard input

        System.out.println("Thanks for the name, " + name);


        return "Hello "+name;
    }
}
