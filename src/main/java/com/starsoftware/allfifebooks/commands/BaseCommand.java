package com.starsoftware.allfifebooks.commands;

import com.starsoftware.allfifebooks.books.BookController;
import com.starsoftware.allfifebooks.books.bookTypes.Book;
import com.starsoftware.allfifebooks.userPrompts.UserPromptFields;
import com.starsoftware.allfifebooks.userPrompts.UserPrompts;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jordan
 * Date: 30/09/2013
 * Time: 19:44
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseCommand implements Command {


    private static final List<String> YES_NO = Arrays.asList("YES", "NO");
    private final static String DELMITER_STRING = "@@@--@@@";
    BookController bookController;

    BaseCommand() {
        bookController = new BookController();
    }

    public boolean validatePrompt(UserPrompts userPrompt) {
        if (userPrompt.getField().equals(UserPromptFields.BOOK_ID)) {
            if (bookController.validateID(userPrompt)) {
                return bookController.isBookInStock(userPrompt);
            }
        } else if (userPrompt.getField().equals(UserPromptFields.BOOK_LISTING)) {
            if (YES_NO.contains(userPrompt.getValue().toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public void displayBookListing() {
        Map<String, Book> bookList = bookController.getBookMap();
        String[] lines = new String[bookList.size() + 1];
        int i = 0;
        lines[i] = ("Book ID" + DELMITER_STRING + "Author" + DELMITER_STRING + "Title" + DELMITER_STRING + "Status");
        for (String s : bookList.keySet()) {
            i++;
            Book displayBook = bookList.get(s);
            lines[i] = (displayBook.getBookId() + DELMITER_STRING + displayBook.getAuthor() + DELMITER_STRING + displayBook.getTitle() + DELMITER_STRING + displayBook.getStatus());
        }
        for (String line : lines) {
            String[] parts = line.split(DELMITER_STRING);
            if (parts[3].equals("NEW") || parts[3].equals("REFURBISHED")) {
                System.out.printf("%-10s %-15s %-20s   %s%n", parts[0], parts[3], parts[1], parts[2]);
            }
        }
    }
}
