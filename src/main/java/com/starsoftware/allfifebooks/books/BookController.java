package com.starsoftware.allfifebooks.books;

import com.starsoftware.allfifebooks.books.bookTypes.Book;
import com.starsoftware.allfifebooks.books.bookTypes.SoldBook;
import com.starsoftware.allfifebooks.commands.Commands;
import com.starsoftware.allfifebooks.persistence.PersistenceHelper;
import com.starsoftware.allfifebooks.userPrompts.UserPromptFields;
import com.starsoftware.allfifebooks.userPrompts.UserPrompts;

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
    Map<String, Book> bookMap;

    public BookController() {
        helper = new PersistenceHelper();
        bookMap = helper.loadBookList();

    }

    public boolean validateID(UserPrompts userPrompt) {
        return bookMap.containsKey(userPrompt.getValue().toUpperCase());
    }

    public boolean save(List<UserPrompts> userPrompts, Commands command) {
        if (command.equals(Commands.ADD)) {
            Book savedBook = new Book();
            for (UserPrompts userPrompt : userPrompts) {
                setStandardBookFields(savedBook, userPrompt);
            }
            return helper.saveNewBook(savedBook);

        }
        if (command.equals(Commands.SELL)) {
            String soldPrice = "";
            String bookId = "";
            for (UserPrompts userPrompt : userPrompts) {
                if (userPrompt.getField().equals(UserPromptFields.PRICE)) {
                    soldPrice = userPrompt.getValue();
                } else if (userPrompt.getField().equals(UserPromptFields.BOOK_ID)) {
                    bookId = userPrompt.getValue();
                }
            }
            Book inStockBook = bookMap.get(bookId);

            SoldBook soldBook = new SoldBook(inStockBook);
            soldBook.setSoldPrice(soldPrice);
            soldBook.setStatus(BookStatuses.SOLD.getStatus());
            bookMap.remove(soldBook.getBookId());

            bookMap.put(soldBook.getBookId(), soldBook);

            return helper.alterBookList(bookMap);

        }
        return false;
    }

    private void setStandardBookFields(Book savedBook, UserPrompts userPrompt) {
        if (userPrompt.getField().equals(UserPromptFields.BOOK_ID)) {
            savedBook.setBookId(userPrompt.getValue());
        } else if (userPrompt.getField().equals(UserPromptFields.STATUS)) {
            savedBook.setStatus(userPrompt.getValue());
        } else if (userPrompt.getField().equals(UserPromptFields.AUTHOR)) {
            savedBook.setAuthor(userPrompt.getValue());

        } else if (userPrompt.getField().equals(UserPromptFields.TITLE)) {
            savedBook.setTitle(userPrompt.getValue());

        }
    }

    public Map<String, Book> getBookMap() {
        return bookMap;  //To change body of created methods use File | Settings | File Templates.
    }
}
