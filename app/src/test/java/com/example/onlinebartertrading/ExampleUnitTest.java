package com.example.onlinebartertrading;

import static com.example.onlinebartertrading.makePostActivity.maxTitleLength;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    static makePostActivity makePostActivity;
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void checkIfTitleIsEmpty(){
        assertFalse(makePostActivity.validTitleDesc(""));
    }

    @Test
    public void checkIfTitlePostIsToLong(){
        String testString = "";
        for (int i=0; i<maxTitleLength+1;i++){
            testString+="1";
        }
        assertFalse(makePostActivity.validTitleDesc(testString));
    }

    @Test
    public void checkIfValidTitlePost(){
        assertTrue(makePostActivity.validTitleDesc("This is a valid title"));
    }
}