package com.starsoftware.allfifebooks.books;


import com.starsoftware.allfifebooks.books.bookTypes.BinnedBook;
import com.starsoftware.allfifebooks.books.bookTypes.Book;
import com.starsoftware.allfifebooks.books.bookTypes.SoldBook;
import com.starsoftware.allfifebooks.commands.Commands;
import com.starsoftware.allfifebooks.persistence.PersistenceHelper;
import com.starsoftware.allfifebooks.userPrompts.UserPromptFields;
import com.starsoftware.allfifebooks.userPrompts.UserPrompts;
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
import java.util.*;

import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class BookControllerTest {
    @Mock
    PersistenceHelper helper;
    UserPrompts bookId;
    UserPrompts author;
    UserPrompts title;
    UserPrompts status;
    UserPrompts soldPrice;
    UserPrompts fault;

    Map<String, Book> bookMap = new HashMap<String, Book>();
    BookController bookController;
    Book newBook;
    Book soldBook;
    Book refurbedBook;
    Book binnedBook;
    Book newBook2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        bookId = new UserPrompts("Enter Book Id", UserPromptFields.BOOK_ID, true);
        title = new UserPrompts("> Please Enter the Book Title", UserPromptFields.TITLE, false);
        author = new UserPrompts("> Please Enter the Authors Name", UserPromptFields.AUTHOR, false);
        status = new UserPrompts(">Is the Book New Or Refurbished?", UserPromptFields.STATUS, false);
        soldPrice = new UserPrompts(">What was it sold for", UserPromptFields.PRICE, false);
        fault = new UserPrompts(">What was wrong with it", UserPromptFields.FAULT, false);

        when(helper.loadBookList()).thenReturn(bookMap);

        bookController = new BookController();
        setUpBooks();
        bookController.bookMap = bookMap;
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
    public void validateID_length() {
        BookController bookController = new BookController();
        bookId.setValue("1234");
        Assert.assertFalse(bookController.validateID(bookId));
        bookId.setValue("1234567890");
        Assert.assertFalse(bookController.validateID(bookId));
        bookId.setValue("123456789");
        Assert.assertTrue(bookController.validateID(bookId));
    }

    @Test
    public void validateID_checkKey_In_BookMap() {
        BookController bookController = new BookController();
        bookController.bookMap = bookMap;
        bookId.setValue("NoT InSET");
        Assert.assertFalse(bookController.validateID(bookId));
        bookId.setValue(newBook.getBookId());
        Assert.assertTrue(bookController.validateID(bookId));

    }

    @Test
    public void testBookInCorrectState() {
        List<String> acceptedStatus = Arrays.asList("NEW", "REFURBISHED");
        bookId.setValue(newBook.getBookId());
        Assert.assertTrue(bookController.isBookInCorrectState(acceptedStatus, bookId));
        bookId.setValue(refurbedBook.getBookId());
        Assert.assertTrue(bookController.isBookInCorrectState(acceptedStatus, bookId));
        bookId.setValue(soldBook.getBookId());
        Assert.assertFalse(bookController.isBookInCorrectState(acceptedStatus, bookId));
        acceptedStatus = Arrays.asList("SOLD");
        Assert.assertTrue(bookController.isBookInCorrectState(acceptedStatus, bookId));

    }

    @Test
    public void testBookReport_AsExpected() {

        List<BookReportable> bookReportables = bookController.generateReport();
        for (BookReportable bookReportable : bookReportables) {
            if (bookReportable.getStatusType().equals(BookStatuses.NEW.getStatus())) {
                Assert.assertEquals((Integer) 2, bookReportable.getCount());
            }
            if (bookReportable.getStatusType().equals(BookStatuses.BINNED.getStatus())) {
                Assert.assertEquals((Integer) 1, bookReportable.getCount());
            }
            if (bookReportable.getStatusType().equals(BookStatuses.SOLD.getStatus())) {
                Assert.assertEquals((Integer) 1, bookReportable.getCount());
            }
            if (bookReportable.getStatusType().equals(BookStatuses.REFURBISHED.getStatus())) {
                Assert.assertEquals((Integer) 1, bookReportable.getCount());
            }
        }

    }

    @Test
    public void testSaveBook() {
        List<UserPrompts> userPrompts = new ArrayList<UserPrompts>();
        bookId.setValue(newBook.getBookId());
        author.setValue(newBook.getAuthor());
        status.setValue(newBook.getStatus());
        title.setValue(newBook.getTitle());
        userPrompts.add(bookId);
        userPrompts.add(author);
        userPrompts.add(title);
        userPrompts.add(status);

        Assert.assertTrue(bookController.save(userPrompts, Commands.ADD));
        Map<String, Book> bookList = bookController.helper.loadBookList();
        Book addedBook = bookList.get(newBook.getBookId());
        Assert.assertEquals(addedBook.getAuthor(), newBook.getAuthor());
    }

    @Test
    public void testSoldBook() {
        List<UserPrompts> userPrompts = new ArrayList<UserPrompts>();
        bookId.setValue(newBook.getBookId());
        soldPrice.setValue(soldBook.getSoldPrice());
        userPrompts.add(bookId);
        userPrompts.add(soldPrice);


        Assert.assertTrue(bookController.save(userPrompts, Commands.SELL));
        Map<String, Book> bookList = bookController.helper.loadBookList();
        Assert.assertTrue(newBook.getStatus().equals(BookStatuses.NEW.getStatus()));
        Book soldBook = bookList.get(newBook.getBookId());
        Assert.assertEquals(soldBook.getAuthor(), newBook.getAuthor());
        Assert.assertEquals(soldBook.getStatus(), BookStatuses.SOLD.getStatus());
        Assert.assertEquals(soldBook.getSoldPrice(), soldBook.getSoldPrice());

    }

    @Test
    public void testBinnedBook() {
        List<UserPrompts> userPrompts = new ArrayList<UserPrompts>();
        bookId.setValue(newBook2.getBookId());
        soldPrice.setValue(binnedBook.getFaultDescription());
        userPrompts.add(bookId);
        userPrompts.add(fault);


        Assert.assertTrue(bookController.save(userPrompts, Commands.BIN));
        Map<String, Book> bookList = bookController.helper.loadBookList();
        Assert.assertTrue(newBook2.getStatus().equals(BookStatuses.NEW.getStatus()));
        Book binnedBook = bookList.get(newBook2.getBookId());
        Assert.assertEquals(binnedBook.getAuthor(), newBook2.getAuthor());
        Assert.assertEquals(binnedBook.getStatus(), BookStatuses.BINNED.getStatus());
        Assert.assertEquals(binnedBook.getFaultDescription(), this.binnedBook.getFaultDescription());

    }

    @Test
    public void testRefurbishBook() {
        List<UserPrompts> userPrompts = new ArrayList<UserPrompts>();
        bookId.setValue(binnedBook.getBookId());
        userPrompts.add(bookId);


        Assert.assertTrue(bookController.save(userPrompts, Commands.REFUBISH));
        Map<String, Book> bookList = bookController.helper.loadBookList();
        Assert.assertTrue(binnedBook.getStatus().equals(BookStatuses.BINNED.getStatus()));
        Book addedBook = bookList.get(binnedBook.getBookId());
        Assert.assertEquals(addedBook.getAuthor(), binnedBook.getAuthor());
        Assert.assertEquals(addedBook.getStatus(), BookStatuses.REFURBISHED.getStatus());

    }

    private void setUpBooks() {
        newBook = new Book();
        newBook.setTitle("A Title");
        newBook.setBookId("TEST1234");
        newBook.setAuthor("A Author");
        newBook.setStatus(BookStatuses.NEW.getStatus());
        newBook2 = new Book();
        newBook2.setTitle("An Other Title");
        newBook2.setBookId("TEST2345");
        newBook2.setAuthor("B Author");
        newBook2.setStatus(BookStatuses.NEW.getStatus());
        refurbedBook = new Book();
        refurbedBook.setTitle("An Refubished Title");
        refurbedBook.setBookId("TEST3456");
        refurbedBook.setAuthor("C Author");
        refurbedBook.setStatus(BookStatuses.REFURBISHED.getStatus());
        soldBook = new SoldBook();
        soldBook.setTitle("An Sole Title");
        soldBook.setBookId("TEST4567");
        soldBook.setAuthor("S Author");
        soldBook.setSoldPrice("£9.99");
        binnedBook = new BinnedBook();
        binnedBook.setTitle("Rubbish Title");
        binnedBook.setBookId("123-123-1");
        binnedBook.setAuthor("Oscar The Grouch");
        binnedBook.setFaultDescription("Ripped");
        bookMap.put(newBook.getBookId(), newBook);
        bookMap.put(newBook2.getBookId(), newBook2);
        bookMap.put(refurbedBook.getBookId(), refurbedBook);
        bookMap.put(soldBook.getBookId(), soldBook);
        bookMap.put(binnedBook.getBookId(), binnedBook);
    }
}
