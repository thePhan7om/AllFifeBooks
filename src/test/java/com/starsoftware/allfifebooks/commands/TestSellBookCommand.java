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
public class TestSellBookCommand {
    SellBookCommand sellBookCommand;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        sellBookCommand = new SellBookCommand();

    }

    @Test
    public void testCreatePrompts() {
        List<UserPrompts> addPrompts = sellBookCommand.executeCommand();
        Assert.assertEquals(3, addPrompts.size());
        Assert.assertEquals(UserPromptFields.BOOK_LISTING, addPrompts.get(0).getField());
        Assert.assertEquals(UserPromptFields.BOOK_ID, addPrompts.get(1).getField());
        Assert.assertEquals(UserPromptFields.PRICE, addPrompts.get(2).getField());
    }

    @Test
    public void testCommand() {
        Assert.assertEquals(Commands.SELL, sellBookCommand.getCommand());
    }

    @Test
    public void testValidatePrompts() {
        List<UserPrompts> addPrompts = sellBookCommand.executeCommand();
        UserPrompts bookId = addPrompts.get(1);
        bookId.setValue("456789123");     //BookId in Use  can be sold
        boolean result = sellBookCommand.validatePrompt(bookId);
        Assert.assertTrue(result);
        bookId.setValue("TEST1234"); //Book id not in use cant be sold
        result = sellBookCommand.validatePrompt(bookId);
        Assert.assertFalse(result);

        UserPrompts displayBooks = addPrompts.get(0);
        displayBooks.setValue("YES");
        result = sellBookCommand.validatePrompt(displayBooks);
        Assert.assertTrue(result);
        displayBooks.setValue("NO");
        result = sellBookCommand.validatePrompt(displayBooks);
        Assert.assertTrue(result);
        displayBooks.setValue("PEW");

        result = sellBookCommand.validatePrompt(displayBooks);
        Assert.assertFalse(result);


    }
}
