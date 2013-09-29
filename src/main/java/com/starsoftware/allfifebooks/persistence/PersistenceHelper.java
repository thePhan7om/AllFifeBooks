package com.starsoftware.allfifebooks.persistence;

import com.starsoftware.allfifebooks.books.Book;
import com.starsoftware.allfifebooks.books.BookStatuses;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Jordan
 * Date: 29/09/2013
 * Time: 19:49
 * To change this template use File | Settings | File Templates.
 */
public class PersistenceHelper {
    private final static String DELMITER_STRING = "@@@--@@@";
    private static final Logger log = Logger.getLogger(PersistenceHelper.class);
    private final static String FILE_NAME = "inventory/allbooks.txt";

    public boolean saveBook(Book savedBook) {

        try {
            Writer output = new BufferedWriter(new FileWriter(FILE_NAME, true));
            output.append(savedBook.getBookId() + DELMITER_STRING + savedBook.getAuthor() + DELMITER_STRING + savedBook.getTitle() + DELMITER_STRING + savedBook.getStatus());
            output.close();
            return true;

        } catch (IOException e) {
            log.error("Book File not found");
            e.printStackTrace();
            return false;
        }
    }

    public Map loadBookList() {
        Map<String, Book> bookMap = new HashMap<String, Book>();
        java.io.File file = new java.io.File("inventory/allbooks.txt");
        System.out.println(file.getAbsolutePath());
        Scanner input = null;
        try {
            input = new Scanner(file);
        } catch (FileNotFoundException e) {
            log.error("Book File not found");
            e.printStackTrace();
        }
        while (input.hasNext()) {
            String bookEntry = input.nextLine();
            Book loadedBook = populateBook(bookEntry);
            bookMap.put(loadedBook.getBookId().toUpperCase(), loadedBook);
        }


        return bookMap;

    }

    private Book populateBook(String bookEntry) {
        log.debug("bookEntry " + bookEntry);

        String[] bookEntryArray = bookEntry.split(DELMITER_STRING);

        if (bookEntry.length() < 4) {
            log.error("Invalid File Entry");


        } else if ((bookEntryArray[3].trim().toUpperCase().equals(BookStatuses.NEW.getStatus())) ||
                (bookEntryArray[3].trim().toUpperCase().equals(BookStatuses.REFURBISHED.getStatus()))) {

            Book newOrRefurbBook = new Book();
            newOrRefurbBook.setBookId(bookEntryArray[0].trim());
            newOrRefurbBook.setAuthor(bookEntryArray[1].trim());
            newOrRefurbBook.setTitle(bookEntryArray[2].trim());
            newOrRefurbBook.setStatus(bookEntryArray[3].trim());
            return newOrRefurbBook;


        } else {
            log.debug("BookEntry3 " + bookEntryArray[3]);
        }
        return null;
    }
}
