package com.starsoftware.allfifebooks.commands;

import com.starsoftware.allfifebooks.books.BookController;

import java.util.ArrayList;
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
        return  createPrompts();

    }

    @Override
    public boolean validatePrompt(UserPrompts userPrompt) {
        return  bookController.validate(userPrompt);
    }

    private List<UserPrompts> createPrompts() {
        List<UserPrompts> userPrompts = new ArrayList<UserPrompts>();
        userPrompts.add(new UserPrompts("Please Enter the Book ID","bookID",true));
        userPrompts.add(new UserPrompts("Please Enter the Book Title","title",false));
        userPrompts.add(new UserPrompts("Please Enter the Authors Name","Author",false));
        userPrompts.add(new UserPrompts("Is the Book New Or Refurbished? Please enter 'N' or 'R'","status",true));
       return userPrompts;

    }


}
