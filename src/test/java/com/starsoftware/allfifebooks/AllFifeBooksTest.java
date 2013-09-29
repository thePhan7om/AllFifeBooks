package com.starsoftware.allfifebooks;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link AllFifeBooks}.
 *
 * @author jordandick@gmail.com (Jordan Dick)
 */
@RunWith(JUnit4.class)
public class AllFifeBooksTest

{
    @Mock
    QuestionAsker questionAsker;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

    }
    @Test
    public void testApplication(){
     AllFifeBooks allFifeBooks = new AllFifeBooks();
     //assertEquals("Hello", allFifeBooks.runApplication(questionAsker));
 }

    @Test
    public void getsIntegerWhenWithinBoundsOfOneToTen() throws Exception {
        QuestionAsker asker = mock(QuestionAsker.class);
        when(questionAsker.ask(anyString())).thenReturn("Fred");
        AllFifeBooks allFifeBooks = new AllFifeBooks();

      //  assertEquals("Hello Fred",allFifeBooks.runApplication(questionAsker));
    }


}