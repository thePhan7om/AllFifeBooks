package com.starsoftware.allfifebooks.persistence;

import com.starsoftware.allfifebooks.books.BookStatuses;
import com.starsoftware.allfifebooks.books.bookTypes.Book;
import com.starsoftware.allfifebooks.books.bookTypes.SoldBook;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class PersistenceHelper {
    private final static String DELMITER_STRING = "@@@--@@@";
    private static final Logger log = Logger.getLogger(PersistenceHelper.class);
    private final static String FILE_NAME = "inventory/allbooks.txt";

    public boolean saveNewBook(Book savedBook) {

        try {
            Writer output = new BufferedWriter(new FileWriter(FILE_NAME, true));
            appendStandardBookDetails(output, savedBook);
            output.append("\n");
            output.close();
            return true;

        } catch (IOException e) {
            log.error("Book File not found");
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterBookList(Map<String, Book> bookMap) {

        try {
            resetFile();
            Writer output = new BufferedWriter(new FileWriter(FILE_NAME, true));

            writeBookMapToFile(bookMap, output);
        } catch (IOException e) {
            log.error("File not found");
        }
        return true;
    }

    private void writeBookMapToFile(Map<String, Book> bookMap, Writer output) throws IOException {
        System.out.println("Book MAP size " + bookMap.size());
        for (Book book : bookMap.values()) {
            if (book instanceof SoldBook) {
                appendStandardBookDetails(output, book);
                output.append(DELMITER_STRING + book.getSoldPrice());
                output.append("\n");
                continue;

            }
            if (book instanceof Book) {
                appendStandardBookDetails(output, book);
                output.append("\n");

            }


        }
        output.close();
    }

    private void appendStandardBookDetails(Writer output, Book book) throws IOException {
        output.append(book.getBookId() + DELMITER_STRING + book.getAuthor() + DELMITER_STRING + book.getTitle() + DELMITER_STRING + book.getStatus());
    }

    private void resetFile() throws IOException {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
        file = new File(FILE_NAME);
        file.createNewFile();
    }

    public Map loadBookList() {
        Map<String, Book> bookMap = new HashMap<String, Book>();
        File file = new File(FILE_NAME);
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


        } else if (bookEntryArray[3].trim().toUpperCase().equals(BookStatuses.SOLD.getStatus())) {

            Book soldBook = new Book();
            soldBook.setBookId(bookEntryArray[0].trim());
            soldBook.setAuthor(bookEntryArray[1].trim());
            soldBook.setTitle(bookEntryArray[2].trim());
            soldBook.setSoldPrice(bookEntryArray[3].trim());
            return soldBook;


        } else {
            log.debug("BookEntry3 " + bookEntryArray[3]);
        }
        return null;
    }


}
