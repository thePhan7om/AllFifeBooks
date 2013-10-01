package com.starsoftware.allfifebooks;


import com.starsoftware.allfifebooks.userPrompts.QuestionAsker;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link AllFifeBooks}.
 *
 * @author jordandick@gmail.com (Jordan Dick)
 */
@RunWith(JUnit4.class)
public class AllFifeBooksAcceptanceTest

{
    public final static String MENU = " Press 1 to add a new book \n Press 2 to sell a book" +
            "\n Press 3 to bin a book \n Press 4 to refurbish a book" +
            "\n Press 5 to view a book report";
    public final static String ENTER_ID = "> Please Enter the Book ID";
    public final static String ENTER_TITLE = "> Please Enter the Book Title";
    public final static String ENTER_AUTHOR = "> Please Enter the Authors Name";
    public final static String ENTER_STATUS = "> Is the Book New Or Refurbished? Please enter 'NEW' or 'REFURBISHED'";
    public final static String SELL_YES_NO = "> Would you like to see a listing of all in stock books? Please enter 'YES' or 'NO' ";
    public final static String SELL_PRICE = "> Please Enter the Sold Price ";
    public final static String FAULT_REASON = "> Please Enter the fault description ";
    public final static String REFURBISH_YES_NO = "> Would you like to see a listing of all binned books?  Please enter 'YES' or 'NO' ";
    public static final String BOOK_REPORT_YES_NO = "> Would you like to see a listing of all books? Please enter 'YES' or 'NO' ";


    @Mock
    QuestionAsker questionAsker;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

    }

    @After
    public void wipeData() throws IOException {
        String real = "inventory/standardData.txt";
        String test = "inventory/allbooks.txt";
        FileChannel source = null;
        FileChannel destination = null;

        try {

            source = new FileInputStream(real).getChannel();

            destination = new FileOutputStream(test).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    @Test
    public void testApplication() {
        AllFifeBooks allFifeBooks = new AllFifeBooks();
        //assertEquals("Hello", allFifeBooks.runApplication(questionAsker));
    }

    @Test
    public void testUserCanAddANewBook() throws Exception {
        QuestionAsker asker = mock(QuestionAsker.class);
        when(questionAsker.ask(MENU)).thenReturn("1");
        when(questionAsker.ask(ENTER_ID)).thenReturn("Accept123");
        when(questionAsker.ask(ENTER_TITLE)).thenReturn("Add Book Acceptance");
        when(questionAsker.ask(ENTER_AUTHOR)).thenReturn("An Author");
        when(questionAsker.ask(ENTER_STATUS)).thenReturn("NEW");

        AllFifeBooks allFifeBooks = new AllFifeBooks();

        Assert.assertEquals(" >> Book Added", allFifeBooks.runApplication(questionAsker));
    }

    @Test
    public void testUserCanAddARefurbishedBook() throws Exception {
        QuestionAsker asker = mock(QuestionAsker.class);
        when(questionAsker.ask(MENU)).thenReturn("1");
        when(questionAsker.ask(ENTER_ID)).thenReturn("Accept234");
        when(questionAsker.ask(ENTER_TITLE)).thenReturn("Add refurbished Book Acceptance");
        when(questionAsker.ask(ENTER_AUTHOR)).thenReturn("An Author");
        when(questionAsker.ask(ENTER_STATUS)).thenReturn("REFURBISHED");

        AllFifeBooks allFifeBooks = new AllFifeBooks();

        Assert.assertEquals(" >> Book Added", allFifeBooks.runApplication(questionAsker));
    }

    @Test
    public void testUserCanSellABook_ViewList() throws Exception {
        QuestionAsker asker = mock(QuestionAsker.class);
        when(questionAsker.ask(MENU)).thenReturn("2");

        when(questionAsker.ask(SELL_YES_NO)).thenReturn("YES");
        when(questionAsker.ask(ENTER_ID)).thenReturn("567891234");
        when(questionAsker.ask(SELL_PRICE)).thenReturn("£9.99");
        AllFifeBooks allFifeBooks = new AllFifeBooks();

        Assert.assertEquals(" >> Book Sold", allFifeBooks.runApplication(questionAsker));
    }

    @Test
    public void testUserCanSellABook_NoList() throws Exception {
        QuestionAsker asker = mock(QuestionAsker.class);
        when(questionAsker.ask(MENU)).thenReturn("2");

        when(questionAsker.ask(SELL_YES_NO)).thenReturn("NO");
        when(questionAsker.ask(ENTER_ID)).thenReturn("567891234");
        when(questionAsker.ask(SELL_PRICE)).thenReturn("£9.99");
        AllFifeBooks allFifeBooks = new AllFifeBooks();

        Assert.assertEquals(" >> Book Sold", allFifeBooks.runApplication(questionAsker));
    }

    @Test
    public void testUserCanBinABook_NoList() throws Exception {
        QuestionAsker asker = mock(QuestionAsker.class);
        when(questionAsker.ask(MENU)).thenReturn("3");

        when(questionAsker.ask(SELL_YES_NO)).thenReturn("NO");
        when(questionAsker.ask(ENTER_ID)).thenReturn("567891234");
        when(questionAsker.ask(FAULT_REASON)).thenReturn("Ripped Cover");
        AllFifeBooks allFifeBooks = new AllFifeBooks();

        Assert.assertEquals(" >> Book Binned", allFifeBooks.runApplication(questionAsker));
    }

    @Test
    public void testUserCanBinABook_ViewList() throws Exception {
        QuestionAsker asker = mock(QuestionAsker.class);
        when(questionAsker.ask(MENU)).thenReturn("3");

        when(questionAsker.ask(SELL_YES_NO)).thenReturn("YES");
        when(questionAsker.ask(ENTER_ID)).thenReturn("567891234");
        when(questionAsker.ask(FAULT_REASON)).thenReturn("Ripped Cover");
        AllFifeBooks allFifeBooks = new AllFifeBooks();

        Assert.assertEquals(" >> Book Binned", allFifeBooks.runApplication(questionAsker));
    }

    @Test
    public void testUserCanRefurbishABook_ViewList() throws Exception {
        QuestionAsker asker = mock(QuestionAsker.class);
        when(questionAsker.ask(MENU)).thenReturn("4");

        when(questionAsker.ask(REFURBISH_YES_NO)).thenReturn("Yes");
        when(questionAsker.ask(ENTER_ID)).thenReturn("678912345");
        AllFifeBooks allFifeBooks = new AllFifeBooks();

        Assert.assertEquals(" >> Book Refurbished", allFifeBooks.runApplication(questionAsker));
    }

    @Test
    public void testUserCanRefurbishABook_NoList() throws Exception {
        QuestionAsker asker = mock(QuestionAsker.class);
        when(questionAsker.ask(MENU)).thenReturn("4");

        when(questionAsker.ask(REFURBISH_YES_NO)).thenReturn("No");
        when(questionAsker.ask(ENTER_ID)).thenReturn("678912345");
        AllFifeBooks allFifeBooks = new AllFifeBooks();

        Assert.assertEquals(" >> Book Refurbished", allFifeBooks.runApplication(questionAsker));
    }

    @Test
    public void testUserCanViewReport() throws Exception {
        QuestionAsker asker = mock(QuestionAsker.class);
        when(questionAsker.ask(MENU)).thenReturn("5");

        when(questionAsker.ask(BOOK_REPORT_YES_NO)).thenReturn("YES");
        AllFifeBooks allFifeBooks = new AllFifeBooks();

        Assert.assertEquals(" >> Report Generated", allFifeBooks.runApplication(questionAsker));
    }
}