package com.starsoftware.allfifebooks.commands;

import com.starsoftware.allfifebooks.books.BookController;
import com.starsoftware.allfifebooks.books.bookTypes.Book;
import com.starsoftware.allfifebooks.userPrompts.UserPromptFields;
import com.starsoftware.allfifebooks.userPrompts.UserPrompts;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


public abstract class BaseCommand implements Command {


    private static final List<String> YES_NO = Arrays.asList("YES", "NO");
    private final static String DELMITER_STRING = "@@@--@@@";
    BookController bookController;

    BaseCommand() {
        bookController = new BookController();
    }

    public boolean validatePrompt(List<String> acceptedStatuses, UserPrompts userPrompt) {
        if (userPrompt.getField().equals(UserPromptFields.BOOK_ID)) {
            if (bookController.validateID(userPrompt)) {
                return bookController.isBookInCorrectState(acceptedStatuses, userPrompt);
            }
        } else if (userPrompt.getField().equals(UserPromptFields.BOOK_LISTING)) {
            if (YES_NO.contains(userPrompt.getValue().toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public void displayBookListing(List<String> acceptedStatuses) {
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
            if (acceptedStatuses.contains(parts[3])) {
                System.out.printf("%-10s %-15s %-20s   %s%n", parts[0], parts[3], parts[1], parts[2]);
            }
        }
    }
}
