package com.starsoftware.allfifebooks;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class AllFifeBooks
{
    private static final Logger log = Logger.getLogger(AllFifeBooks.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();

        AllFifeBooks allFifeBooks = new AllFifeBooks();
        allFifeBooks.runApplication();

    }

    public String runApplication() {
        log.debug("Hello");
        return "Hello";
    }
}
