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
public class TestAddBookCommand {
    AddBookCommand addCommand;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        addCommand = new AddBookCommand();

    }

    @Test
    public void testCreatePrompts() {
        AddBookCommand addCommand = new AddBookCommand();
        List<UserPrompts> addPrompts = addCommand.executeCommand();
        Assert.assertEquals(4, addPrompts.size());
        Assert.assertEquals(UserPromptFields.BOOK_ID, addPrompts.get(0).getField());
        Assert.assertEquals(UserPromptFields.TITLE, addPrompts.get(1).getField());
        Assert.assertEquals(UserPromptFields.AUTHOR, addPrompts.get(2).getField());
        Assert.assertEquals(UserPromptFields.STATUS, addPrompts.get(3).getField());

    }

    @Test
    public void testCommand() {
        Assert.assertEquals(Commands.ADD, addCommand.getCommand());
    }

    @Test
    public void testValidatePrompts() {
        List<UserPrompts> addPrompts = addCommand.executeCommand();
        UserPrompts bookId = addPrompts.get(0);
        bookId.setValue("123456789");     //BookId in Use
        boolean result = addCommand.validatePrompt(bookId);
        Assert.assertFalse(result);
        bookId.setValue("TEST1234"); //Book id not in use
        result = addCommand.validatePrompt(bookId);
        Assert.assertTrue(result);

    }
}
