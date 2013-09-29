package com.starsoftware.allfifebooks.commands;

import com.starsoftware.allfifebooks.books.BookController;
import com.starsoftware.allfifebooks.books.bookTypes.Book;
import com.starsoftware.allfifebooks.userPrompts.UserPromptFields;
import com.starsoftware.allfifebooks.userPrompts.UserPrompts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jordan
 * Date: 29/09/2013
 * Time: 22:03
 * To change this template use File | Settings | File Templates.
 */
public class SellBookCommand implements Command {

    private static final List<String> YES_NO = Arrays.asList("YES", "NO");
    BookController bookController;
    private final static String DELMITER_STRING = "@@@--@@@";


    @Override
    public List<UserPrompts> executeCommand() {
        bookController = new BookController();
        return createPrompts();
    }

    @Override
    public boolean validatePrompt(UserPrompts userPrompt) {
        if (userPrompt.getField().equals(UserPromptFields.BOOK_ID)) {
            return bookController.validateID(userPrompt);
        } else if (userPrompt.getField().equals(UserPromptFields.BOOK_LISTING)) {
            System.out.println("Entered field " + userPrompt.getValue().trim().toUpperCase());
            if (YES_NO.contains(userPrompt.getValue().toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void save(List<UserPrompts> userPrompts) {
        boolean result = bookController.save(userPrompts, Commands.SELL);
        if (result) {
            System.out.println("Book Sold");

        }
    }

    @Override
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
            System.out.printf("%-10s %-15s %-20s   %s%n", parts[0], parts[3], parts[1], parts[2]);
        }
    }

    private List<UserPrompts> createPrompts() {
        List<UserPrompts> userPrompts = new ArrayList<UserPrompts>();
        userPrompts.add(new UserPrompts("> Would you like to see a listing of all books? Please enter 'YES' or 'NO' ", UserPromptFields.BOOK_LISTING, true));
        userPrompts.add(new UserPrompts("> Please Enter the Book ID", UserPromptFields.BOOK_ID, true));
        userPrompts.add(new UserPrompts("> Please Enter the Sold Price ", UserPromptFields.PRICE, false));

        return userPrompts;

    }

}
