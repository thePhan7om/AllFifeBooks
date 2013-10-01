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

public class TestBinBookCommand {
    BinBookCommand binBookCommand;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        binBookCommand = new BinBookCommand();
    }

    @Test
    public void testCreatePrompts() {
        List<UserPrompts> binPrompts = binBookCommand.executeCommand();
        Assert.assertEquals(3, binPrompts.size());
        Assert.assertEquals(UserPromptFields.BOOK_LISTING, binPrompts.get(0).getField());
        Assert.assertEquals(UserPromptFields.BOOK_ID, binPrompts.get(1).getField());
        Assert.assertEquals(UserPromptFields.FAULT, binPrompts.get(2).getField());
    }

    @Test
    public void testCommand() {
        Assert.assertEquals(Commands.BIN, binBookCommand.getCommand());
    }

    @Test
    public void testValidatePrompts() {
        List<UserPrompts> addPrompts = binBookCommand.executeCommand();
        UserPrompts bookId = addPrompts.get(1);
        bookId.setValue("456789123");     //BookId in Use  can be sold
        boolean result = binBookCommand.validatePrompt(bookId);
        Assert.assertTrue(result);
        bookId.setValue("TEST1234"); //Book id not in use cant be sold
        result = binBookCommand.validatePrompt(bookId);
        Assert.assertFalse(result);

        UserPrompts displayBooks = addPrompts.get(0);
        displayBooks.setValue("YES");
        result = binBookCommand.validatePrompt(displayBooks);
        Assert.assertTrue(result);
        displayBooks.setValue("NO");
        result = binBookCommand.validatePrompt(displayBooks);
        Assert.assertTrue(result);
        displayBooks.setValue("PEW");

        result = binBookCommand.validatePrompt(displayBooks);
        Assert.assertFalse(result);


    }


}
