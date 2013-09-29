package com.starsoftware.allfifebooks.books;

/**
 * Created with IntelliJ IDEA.
 * User: Jordan
 * Date: 29/09/2013
 * Time: 20:28
 * To change this template use File | Settings | File Templates.
 */
public enum BookStatuses {
    NEW("NEW"),
    REFURBISHED("REFURBISHED"),
    SOLD("SOLD"),
    BINNED("BINNED");

    private String status;

    BookStatuses(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
