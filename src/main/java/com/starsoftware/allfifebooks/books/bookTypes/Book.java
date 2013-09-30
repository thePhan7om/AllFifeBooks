package com.starsoftware.allfifebooks.books.bookTypes;

/**
 * Created with IntelliJ IDEA.
 * User: Jordan
 * Date: 29/09/2013
 * Time: 20:07
 * To change this template use File | Settings | File Templates.
 */
public class Book {

    String bookId;
    String title;
    String author;
    String status;
    String soldPrice;
    String faultDescription;

    public Book() {
    }

    public Book(Book book) {
        this.setBookId(book.getBookId());
        this.setAuthor(book.getAuthor());
        this.setTitle(book.getTitle());
    }

    public String getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(String soldPrice) {
        this.soldPrice = soldPrice;
    }

    public String getFaultDescription() {
        return faultDescription;
    }

    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
