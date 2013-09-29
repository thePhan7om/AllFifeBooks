package com.starsoftware.allfifebooks.books;

import com.starsoftware.allfifebooks.commands.Commands;
import com.starsoftware.allfifebooks.commands.UserPromptFields;
import com.starsoftware.allfifebooks.commands.UserPrompts;
import com.starsoftware.allfifebooks.persistence.PersistenceHelper;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jordan
 * Date: 29/09/2013
 * Time: 19:47
 * To change this template use File | Settings | File Templates.
 */
public class BookController {
    PersistenceHelper helper;
    Map bookMap;

    public BookController() {
        helper = new PersistenceHelper();
        bookMap = helper.loadBookList();

    }

    public boolean validateID(UserPrompts userPrompt) {
        return !bookMap.containsKey(userPrompt.getValue().toUpperCase());
    }


    public boolean save(List<UserPrompts> userPrompts, Commands command) {
        Book savedBook;
        if (command.equals(Commands.ADD)) {
            savedBook = new Book();
            for (UserPrompts userPrompt : userPrompts) {
                setStandardBookFields(savedBook, userPrompt);
            }
            return helper.saveBook(savedBook);

        }
        return false;
    }

    private void setStandardBookFields(Book savedBook, UserPrompts userPrompt) {
        if (userPrompt.getField().equals(UserPromptFields.BOOK_ID.getField())) {
            savedBook.setBookId(userPrompt.getValue().trim());
        } else if (userPrompt.getField().equals(UserPromptFields.STATUS.getField())) {
            savedBook.setStatus(userPrompt.getValue().trim());
        } else if (userPrompt.getField().equals(UserPromptFields.AUTHOR.getField())) {
            savedBook.setAuthor(userPrompt.getValue().trim());

        } else if (userPrompt.getField().equals(UserPromptFields.TITLE.getField())) {
            savedBook.setTitle(userPrompt.getValue().trim());

        }
    }
}
