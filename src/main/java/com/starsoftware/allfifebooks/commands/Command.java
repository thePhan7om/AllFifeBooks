package com.starsoftware.allfifebooks.commands;

import com.starsoftware.allfifebooks.userPrompts.UserPrompts;

import java.util.List;


public interface Command {
    public List<UserPrompts> executeCommand();

    boolean validatePrompt(UserPrompts userPrompt);

    String save(List<UserPrompts> userPrompts);

    void displayBookListing();

    Commands getCommand();
}
