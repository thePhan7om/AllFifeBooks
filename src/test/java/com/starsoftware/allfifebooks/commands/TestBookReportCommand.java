package com.starsoftware.allfifebooks.commands;

import com.starsoftware.allfifebooks.userPrompts.UserPromptFields;
import com.starsoftware.allfifebooks.userPrompts.UserPrompts;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.MockitoAnnotations;

import java.util.List;

@RunWith(JUnit4.class)
public class TestBookReportCommand {
    BookReportCommand bookReportCommand;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        bookReportCommand = new BookReportCommand();

    }

    @Test
    public void testCreatePrompts() {
        List<UserPrompts> addPrompts = bookReportCommand.executeCommand();
        Assert.assertEquals(1, addPrompts.size());
        Assert.assertEquals(UserPromptFields.BOOK_LISTING, addPrompts.get(0).getField());


    }

    @Test
    public void testCommand() {
        Assert.assertEquals(Commands.REPORT, bookReportCommand.getCommand());
    }

    @Test
    public void testValidatePrompts() {
        List<UserPrompts> refurbishPrompts = bookReportCommand.executeCommand();
        boolean result;

        UserPrompts displayBooks = refurbishPrompts.get(0);
        displayBooks.setValue("YES");
        result = bookReportCommand.validatePrompt(displayBooks);
        Assert.assertTrue(result);
        displayBooks.setValue("NO");
        result = bookReportCommand.validatePrompt(displayBooks);
        Assert.assertTrue(result);
        displayBooks.setValue("PEW");

        result = bookReportCommand.validatePrompt(displayBooks);
        Assert.assertFalse(result);
    }
}
