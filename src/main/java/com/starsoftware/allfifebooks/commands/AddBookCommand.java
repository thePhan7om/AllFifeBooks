package com.starsoftware.allfifebooks.commands;

import com.starsoftware.allfifebooks.books.BookController;
import com.starsoftware.allfifebooks.userPrompts.UserPromptFields;
import com.starsoftware.allfifebooks.userPrompts.UserPrompts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jordan
 * Date: 28/09/2013
 * Time: 21:50
 * To change this template use File | Settings | File Templates.
 */
public class AddBookCommand implements Command {
    private static final List<String> ACCEPTED_STATUSES = Arrays.asList("NEW", "REFURB");
    BookController bookController;

    @Override
    public List<UserPrompts> executeCommand() {
        bookController = new BookController();
        return createPrompts();
    }

    @Override
    public boolean validatePrompt(UserPrompts userPrompt) {
        if (userPrompt.getField().equals(UserPromptFields.BOOK_ID)) {
            return !bookController.validateID(userPrompt);
        } else if (userPrompt.getField().equals(UserPromptFields.STATUS)) {
            System.out.println("Entered field " + userPrompt.getValue().trim().toUpperCase());
            if (ACCEPTED_STATUSES.contains(userPrompt.getValue().trim().toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void save(List<UserPrompts> userPrompts) {
        boolean result = bookController.save(userPrompts, Commands.ADD);
        if (result) {
            System.out.println("Book Added");

        }
    }

    @Override
    public void displayBookListing() {
        //Not Used here
    }

    private List<UserPrompts> createPrompts() {
        List<UserPrompts> userPrompts = new ArrayList<UserPrompts>();
        userPrompts.add(new UserPrompts("> Please Enter the Book ID", UserPromptFields.BOOK_ID, true));
        userPrompts.add(new UserPrompts("> Please Enter the Book Title", UserPromptFields.TITLE, false));
        userPrompts.add(new UserPrompts("> Please Enter the Authors Name", UserPromptFields.AUTHOR, false));
        userPrompts.add(new UserPrompts("> Is the Book New Or Refurbished? Please enter 'NEW' or 'REFURB'", UserPromptFields.STATUS, true));
        return userPrompts;

    }


}
