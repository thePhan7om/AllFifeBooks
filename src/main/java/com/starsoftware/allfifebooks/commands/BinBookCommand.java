package com.starsoftware.allfifebooks.commands;

import com.starsoftware.allfifebooks.userPrompts.UserPromptFields;
import com.starsoftware.allfifebooks.userPrompts.UserPrompts;

import java.util.ArrayList;
import java.util.List;


public class BinBookCommand extends BaseCommand implements Command {

    @Override
    public List<UserPrompts> executeCommand() {
        return createPrompts();
    }

    @Override
    public void save(List<UserPrompts> userPrompts) {
        boolean result = bookController.save(userPrompts, Commands.BIN);
        if (result) {
            System.out.println("Book Binned");

        }
    }

    private List<UserPrompts> createPrompts() {
        List<UserPrompts> userPrompts = new ArrayList<UserPrompts>();
        userPrompts.add(new UserPrompts("> Would you like to see a listing of all in stock books? Please enter 'YES' or 'NO' ", UserPromptFields.BOOK_LISTING, true));
        userPrompts.add(new UserPrompts("> Please Enter the Book ID", UserPromptFields.BOOK_ID, true));
        userPrompts.add(new UserPrompts("> Please Enter the fault description ", UserPromptFields.FAULT, false));
        return userPrompts;
    }

    @Override
    public Commands getCommand() {
        return Commands.BIN;
    }
}
