package com.starsoftware.allfifebooks.books;

import com.starsoftware.allfifebooks.commands.UserPrompts;
import com.starsoftware.allfifebooks.persistence.PersistenceHelper;
import org.apache.log4j.helpers.LogLog;

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

    public boolean validate(UserPrompts userPrompt) {
        return !bookMap.containsKey(userPrompt.getValue().toUpperCase());
    }
}
