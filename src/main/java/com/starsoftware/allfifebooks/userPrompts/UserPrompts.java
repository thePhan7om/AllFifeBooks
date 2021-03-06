package com.starsoftware.allfifebooks.userPrompts;

/**
 * Created with IntelliJ IDEA.
 * User: Jordan
 * Date: 29/09/2013
 * Time: 16:13
 * To change this template use File | Settings | File Templates.
 */
public class UserPrompts {
    private String message;


    private UserPromptFields field;
    private String value;
    private Boolean requiresValidation;

    public UserPrompts(String message, UserPromptFields field, boolean requiresValidation) {
        this.message = message;
        this.field = field;
        this.requiresValidation = requiresValidation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getValue() {
        return value.trim();
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getRequiresValidation() {
        return requiresValidation;
    }

    public void setRequiresValidation(Boolean requiresValidation) {
        this.requiresValidation = requiresValidation;
    }

    public UserPromptFields getField() {
        return field;
    }

    public void setField(UserPromptFields field) {
        this.field = field;
    }


}
