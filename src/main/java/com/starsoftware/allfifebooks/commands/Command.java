package com.starsoftware.allfifebooks.commands;

import com.starsoftware.allfifebooks.userPrompts.UserPrompts;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jordan
 * Date: 28/09/2013
 * Time: 21:49
 * To change this template use File | Settings | File Templates.
 */
public interface Command {
    public List<UserPrompts> executeCommand();

    boolean validatePrompt(UserPrompts userPrompt);

    void save(List<UserPrompts> userPrompts);

    void displayBookListing();
}
