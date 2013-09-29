package com.starsoftware.allfifebooks.books.bookTypes;

/**
 * Created with IntelliJ IDEA.
 * User: Jordan
 * Date: 29/09/2013
 * Time: 23:19
 * To change this template use File | Settings | File Templates.
 */
public class SoldBook extends Book {
    private String soldPrice;

    public String getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(String soldPrice) {
        this.soldPrice = soldPrice;
    }

}
