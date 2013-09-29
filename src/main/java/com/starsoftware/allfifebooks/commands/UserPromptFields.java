package com.starsoftware.allfifebooks.commands;

/**
 * Created with IntelliJ IDEA.
 * User: Jordan
 * Date: 29/09/2013
 * Time: 21:14
 * To change this template use File | Settings | File Templates.
 */
public enum UserPromptFields {

    BOOK_ID("BOOK_ID"),
    AUTHOR("AUTHOR"),
    TITLE("TITLE"),
    STATUS("STATUS");

    private String field;

    UserPromptFields(String fields) {
        this.field = fields;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

}
