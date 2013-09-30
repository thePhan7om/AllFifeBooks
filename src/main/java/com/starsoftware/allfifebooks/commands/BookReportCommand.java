package com.starsoftware.allfifebooks.commands;

import com.starsoftware.allfifebooks.books.BookReportable;
import com.starsoftware.allfifebooks.userPrompts.UserPromptFields;
import com.starsoftware.allfifebooks.userPrompts.UserPrompts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jordan
 * Date: 30/09/2013
 * Time: 20:38
 * To change this template use File | Settings | File Templates.
 */
public class BookReportCommand extends BaseCommand implements Command {
    private static final List<String> YES_NO = Arrays.asList("YES", "NO");

    public List<UserPrompts> executeCommand() {
        return createPrompts();
    }

    public boolean validatePrompt(UserPrompts userPrompt) {
        if (userPrompt.getField().equals(UserPromptFields.BOOK_LISTING)) {
            if (YES_NO.contains(userPrompt.getValue().toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void save(List<UserPrompts> userPrompts) {

    }

    public void displayBookListing() {
        List<BookReportable> bookReport = bookController.generateReport();
        String[] lines = new String[bookReport.size() + 1];
        int i = 0;
        lines[i] = ("STATUS= Count");

        for (BookReportable bookReportable : bookReport) {
            i++;
            lines[i] = (bookReportable.getStatusType() + "=" + bookReportable.getCount());
        }
        for (String line : lines) {
            String[] parts = line.split("=");
            System.out.printf("%-10s %s%n", parts[0], parts[1]);
        }

    }

    private List<UserPrompts> createPrompts() {
        List<UserPrompts> userPrompts = new ArrayList<UserPrompts>();
        userPrompts.add(new UserPrompts("> Would you like to see a listing of all books? Please enter 'YES' or 'NO' ", UserPromptFields.BOOK_LISTING, true));
        return userPrompts;
    }

    @Override
    public Commands getCommand() {
        return Commands.REPORT;
    }

}


