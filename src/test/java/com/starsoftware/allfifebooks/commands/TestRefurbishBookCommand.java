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
public class TestRefurbishBookCommand {
    RefurbishBookCommand refurbishBookCommand;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        refurbishBookCommand = new RefurbishBookCommand();

    }

    @Test
    public void testCreatePrompts() {
        List<UserPrompts> addPrompts = refurbishBookCommand.executeCommand();
        Assert.assertEquals(2, addPrompts.size());
        Assert.assertEquals(UserPromptFields.BOOK_LISTING, addPrompts.get(0).getField());
        Assert.assertEquals(UserPromptFields.BOOK_ID, addPrompts.get(1).getField());


    }

    @Test
    public void testCommand() {
        Assert.assertEquals(Commands.REFUBISH, refurbishBookCommand.getCommand());
    }

    @Test
    public void testValidatePrompts() {
        List<UserPrompts> refurbishPrompts = refurbishBookCommand.executeCommand();
        UserPrompts bookId = refurbishPrompts.get(1);
        bookId.setValue("678912345");     //BookId is Binned
        boolean result = refurbishBookCommand.validatePrompt(bookId);
        Assert.assertTrue(result);
        bookId.setValue("456789123"); //Book id not binned
        result = refurbishBookCommand.validatePrompt(bookId);
        Assert.assertFalse(result);

        UserPrompts displayBooks = refurbishPrompts.get(0);
        displayBooks.setValue("YES");
        result = refurbishBookCommand.validatePrompt(displayBooks);
        Assert.assertTrue(result);
        displayBooks.setValue("NO");
        result = refurbishBookCommand.validatePrompt(displayBooks);
        Assert.assertTrue(result);
        displayBooks.setValue("PEW");

        result = refurbishBookCommand.validatePrompt(displayBooks);
        Assert.assertFalse(result);
    }
}
