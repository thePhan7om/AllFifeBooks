package com.starsoftware.allfifebooks.books.bookTypes;

import com.starsoftware.allfifebooks.books.BookStatuses;

/**
 * Created with IntelliJ IDEA.
 * User: Jordan
 * Date: 30/09/2013
 * Time: 19:57
 * To change this template use File | Settings | File Templates.
 */
public class BinnedBook extends Book {
    public BinnedBook() {
    }

    public BinnedBook(Book inStockBook) {
        this.setBookId(inStockBook.getBookId());
        this.setAuthor(inStockBook.getAuthor());
        this.setTitle(inStockBook.getTitle());

    }

    @Override
    public String getStatus() {
        return BookStatuses.BINNED.getStatus();
    }

    @Override
    public String getFaultDescription() {
        return super.getFaultDescription();
    }

    @Override
    public void setFaultDescription(String faultDescription) {
        super.setFaultDescription(faultDescription);
    }
}
