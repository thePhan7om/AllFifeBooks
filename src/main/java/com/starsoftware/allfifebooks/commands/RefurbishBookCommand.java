package com.starsoftware.allfifebooks.commands;

import com.starsoftware.allfifebooks.userPrompts.UserPromptFields;
import com.starsoftware.allfifebooks.userPrompts.UserPrompts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jordan
 * Date: 30/09/2013
 * Time: 20:10
 * To change this template use File | Settings | File Templates.
 */
public class RefurbishBookCommand extends BaseCommand implements Command {
    private static final List<String> ACCEPTED_STATUSES = Arrays.asList("BINNED");

    public List<UserPrompts> executeCommand() {
        return createPrompts();
    }

    public boolean validatePrompt(UserPrompts userPrompt) {
        return super.validatePrompt(ACCEPTED_STATUSES, userPrompt);
    }

    @Override
    public void save(List<UserPrompts> userPrompts) {
        boolean result = bookController.save(userPrompts, Commands.REFUBISH);
        if (result) {
            System.out.println(" >> Book Refurbished");
        }
    }

    public void displayBookListing() {
        super.displayBookListing(ACCEPTED_STATUSES);
    }

    private List<UserPrompts> createPrompts() {
        List<UserPrompts> userPrompts = new ArrayList<UserPrompts>();
        userPrompts.add(new UserPrompts("> Would you like to see a listing of all binned books?  Please enter 'YES' or 'NO' ", UserPromptFields.BOOK_LISTING, true));
        userPrompts.add(new UserPrompts("> Please Enter the Book ID", UserPromptFields.BOOK_ID, true));
        return userPrompts;
    }

    @Override
    public Commands getCommand() {
        return Commands.REFUBISH;
    }
}