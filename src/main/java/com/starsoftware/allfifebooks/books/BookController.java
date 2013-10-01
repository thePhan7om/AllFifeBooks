package com.starsoftware.allfifebooks.books;

import com.starsoftware.allfifebooks.books.bookTypes.BinnedBook;
import com.starsoftware.allfifebooks.books.bookTypes.Book;
import com.starsoftware.allfifebooks.books.bookTypes.SoldBook;
import com.starsoftware.allfifebooks.commands.Commands;
import com.starsoftware.allfifebooks.persistence.PersistenceHelper;
import com.starsoftware.allfifebooks.userPrompts.UserPromptFields;
import com.starsoftware.allfifebooks.userPrompts.UserPrompts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BookController {
    PersistenceHelper helper;
    Map<String, Book> bookMap;

    public BookController() {
        helper = new PersistenceHelper();
        bookMap = helper.loadBookList();

    }

    public boolean validateID(UserPrompts userPrompt) {
        if (userPrompt.getValue().length() != 9) {
            return false;
        }

        return bookMap.containsKey(userPrompt.getValue().toUpperCase());
    }

    public boolean isBookInCorrectState(List<String> acceptedStatuses, UserPrompts userPrompt) {
        Book selectedBook = bookMap.get(userPrompt.getValue().toUpperCase());
        return acceptedStatuses.contains(selectedBook.getStatus());
    }

    public boolean save(List<UserPrompts> userPrompts, Commands command) {
        if (command.equals(Commands.ADD)) {
            return addBook(userPrompts);

        }
        if (command.equals(Commands.SELL)) {
            return sellBook(userPrompts);

        }
        if (command.equals(Commands.BIN)) {
            return binBook(userPrompts);

        }
        if (command.equals(Commands.REFUBISH)) {
            return refurbishBook(userPrompts);

        }
        return false;
    }

    private boolean refurbishBook(List<UserPrompts> userPrompts) {
        String bookId = "";
        for (UserPrompts userPrompt : userPrompts) {
            if (userPrompt.getField().equals(UserPromptFields.BOOK_ID)) {
                bookId = userPrompt.getValue();
            }
        }
        Book refurbishedBook = new Book(bookMap.get(bookId));
        refurbishedBook.setStatus(BookStatuses.REFURBISHED.getStatus());
        bookMap.remove(refurbishedBook.getBookId());
        bookMap.put(refurbishedBook.getBookId(), refurbishedBook);
        return helper.alterBookList(bookMap);
    }

    private boolean sellBook(List<UserPrompts> userPrompts) {
        String soldPrice = "";
        String bookId = "";
        for (UserPrompts userPrompt : userPrompts) {
            if (userPrompt.getField().equals(UserPromptFields.PRICE)) {
                soldPrice = userPrompt.getValue();
            } else if (userPrompt.getField().equals(UserPromptFields.BOOK_ID)) {
                bookId = userPrompt.getValue();
            }
        }


        Book inStockBook = bookMap.get(bookId.toUpperCase());
        System.out.println(inStockBook.getBookId());


        SoldBook soldBook = new SoldBook(inStockBook);
        soldBook.setSoldPrice(soldPrice);
        soldBook.setStatus(BookStatuses.SOLD.getStatus());
        bookMap.remove(soldBook.getBookId());

        bookMap.put(soldBook.getBookId(), soldBook);

        return helper.alterBookList(bookMap);
    }

    private boolean binBook(List<UserPrompts> userPrompts) {
        String faultDesc = "";
        String bookId = "";
        for (UserPrompts userPrompt : userPrompts) {
            if (userPrompt.getField().equals(UserPromptFields.FAULT)) {
                faultDesc = userPrompt.getValue();
            } else if (userPrompt.getField().equals(UserPromptFields.BOOK_ID)) {
                bookId = userPrompt.getValue();
            }
        }
        Book inStockBook = bookMap.get(bookId);

        BinnedBook binnedBook = new BinnedBook(inStockBook);
        binnedBook.setFaultDescription(faultDesc);
        binnedBook.setStatus(BookStatuses.BINNED.getStatus());
        bookMap.remove(binnedBook.getBookId());
        bookMap.put(binnedBook.getBookId(), binnedBook);
        return helper.alterBookList(bookMap);
    }

    private boolean addBook(List<UserPrompts> userPrompts) {
        Book savedBook = new Book();
        for (UserPrompts userPrompt : userPrompts) {
            setStandardBookFields(savedBook, userPrompt);
        }
        return helper.saveNewBook(savedBook);
    }

    private void setStandardBookFields(Book savedBook, UserPrompts userPrompt) {
        if (userPrompt.getField().equals(UserPromptFields.BOOK_ID)) {
            savedBook.setBookId(userPrompt.getValue().toUpperCase());
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


    public List<BookReportable> generateReport() {
        int newBooks = 0;
        int soldBooks = 0;
        int binnedBooks = 0;
        int refurbishedBooks = 0;
        List<BookReportable> bookReport = new ArrayList<BookReportable>();

        for (Book book : bookMap.values()) {
            if (book.getStatus().equals(BookStatuses.NEW.getStatus())) {
                newBooks++;
            } else if (book.getStatus().equals(BookStatuses.SOLD.getStatus())) {
                soldBooks++;
            } else if (book.getStatus().equals(BookStatuses.BINNED.getStatus())) {
                binnedBooks++;
            } else if (book.getStatus().equals(BookStatuses.REFURBISHED.getStatus())) {
                refurbishedBooks++;
            }

        }
        bookReport.add(new BookReportable(BookStatuses.NEW.getStatus(), newBooks));
        bookReport.add(new BookReportable(BookStatuses.REFURBISHED.getStatus(), refurbishedBooks));
        bookReport.add(new BookReportable(BookStatuses.SOLD.getStatus(), soldBooks));
        bookReport.add(new BookReportable(BookStatuses.BINNED.getStatus(), binnedBooks));

        return bookReport;
    }
}
