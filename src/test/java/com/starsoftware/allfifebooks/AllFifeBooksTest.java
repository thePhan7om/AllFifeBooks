package com.starsoftware.allfifebooks;


import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for {@link AllFifeBooks}.
 *
 * @author jordandick@gmail.com (Jordan Dick)
 */
@RunWith(JUnit4.class)
public class AllFifeBooksTest

{
    @Test
    public void testApplication(){
     AllFifeBooks allFifeBooks = new AllFifeBooks();
     Assert.assertEquals("Hello", allFifeBooks.runApplication());
 }
//}

}