package com.starsoftware.allfifebooks.commands;

import com.starsoftware.allfifebooks.books.BookController;

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
    BookController bookController;

    private static final List<String> ACCEPTED_STATUSES = Arrays.asList("NEW", "REFURB");

    @Override
    public List<UserPrompts> executeCommand() {
     /*
     Things this needs to do:
     capture user inputs for:
     ID, Title Author Status
    Create new Book Object
    Create Prompts
    Create Status Enum
      */

        bookController = new BookController();
        return createPrompts();

    }

    @Override
    public boolean validatePrompt(UserPrompts userPrompt) {
        if (userPrompt.getField().equals(UserPromptFields.BOOK_ID.getField())) {
            return bookController.validateID(userPrompt);
        } else if (userPrompt.getField().equals(UserPromptFields.STATUS.getField())) {

            System.out.println("Entered field " + userPrompt.getValue().trim().toUpperCase());
            if (ACCEPTED_STATUSES.contains(userPrompt.getValue().trim().toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    private List<UserPrompts> createPrompts() {
        List<UserPrompts> userPrompts = new ArrayList<UserPrompts>();
        userPrompts.add(new UserPrompts("> Please Enter the Book ID", UserPromptFields.BOOK_ID.getField(), true));
        userPrompts.add(new UserPrompts("> Please Enter the Book Title", UserPromptFields.TITLE.getField(), false));
        userPrompts.add(new UserPrompts("> Please Enter the Authors Name", UserPromptFields.AUTHOR.getField(), false));
        userPrompts.add(new UserPrompts("> Is the Book New Or Refurbished? Please enter 'NEW' or 'REFURB'", UserPromptFields.STATUS.getField(), true));
        return userPrompts;

    }


}
