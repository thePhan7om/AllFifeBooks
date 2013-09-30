package com.starsoftware.allfifebooks.books.bookTypes;

import com.starsoftware.allfifebooks.books.BookStatuses;

/**
 * Created with IntelliJ IDEA.
 * User: Jordan
 * Date: 29/09/2013
 * Time: 23:19
 * To change this template use File | Settings | File Templates.
 */
public class SoldBook extends Book {

    public SoldBook(Book inStockBook) {
        this.setBookId(inStockBook.getBookId());
        this.setAuthor(inStockBook.getAuthor());
        this.setTitle(inStockBook.getTitle());

    }

    @Override
    public String getStatus() {
        return BookStatuses.SOLD.getStatus();
    }

    @Override
    public String getSoldPrice() {
        return super.getSoldPrice();
    }

    @Override
    public void setSoldPrice(String soldPrice) {
        super.setSoldPrice(soldPrice);
    }

}
