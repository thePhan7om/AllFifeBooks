package com.starsoftware.allfifebooks.books;

/**
 * Created with IntelliJ IDEA.
 * User: Jordan
 * Date: 30/09/2013
 * Time: 20:47
 * To change this template use File | Settings | File Templates.
 */
public class BookReportable {
    private String statusType;
    private Integer count;

    public BookReportable(String statusType, Integer count) {
        this.statusType = statusType;
        this.count = count;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
