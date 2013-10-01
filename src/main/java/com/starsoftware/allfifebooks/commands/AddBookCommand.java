package com.starsoftware.allfifebooks.commands;

import com.starsoftware.allfifebooks.userPrompts.UserPromptFields;
import com.starsoftware.allfifebooks.userPrompts.UserPrompts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AddBookCommand extends BaseCommand implements Command {
    private static final List<String> ACCEPTED_STATUSES = Arrays.asList("NEW", "REFURBISHED");

    @Override
    public List<UserPrompts> executeCommand() {
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
    public String save(List<UserPrompts> userPrompts) {
        boolean result = bookController.save(userPrompts, Commands.ADD);
        if (result) {
            return " >> Book Added";

        }
        return ">> Unable to add book";
    }

    @Override
    public void displayBookListing() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Commands getCommand() {
        return Commands.ADD;
    }

    private List<UserPrompts> createPrompts() {
        List<UserPrompts> userPrompts = new ArrayList<UserPrompts>();
        userPrompts.add(new UserPrompts("> Please Enter the Book ID", UserPromptFields.BOOK_ID, true));
        userPrompts.add(new UserPrompts("> Please Enter the Book Title", UserPromptFields.TITLE, false));
        userPrompts.add(new UserPrompts("> Please Enter the Authors Name", UserPromptFields.AUTHOR, false));
        userPrompts.add(new UserPrompts("> Is the Book New Or Refurbished? Please enter 'NEW' or 'REFURBISHED'", UserPromptFields.STATUS, true));
        return userPrompts;

    }


}
