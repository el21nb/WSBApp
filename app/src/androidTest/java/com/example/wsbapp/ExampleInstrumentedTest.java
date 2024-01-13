package com.example.wsbapp;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    ChildProvider provider ;
    @Before
    public void setUp() throws Exception {
        provider = new ChildProvider();
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void TwoMakeChildren()
    {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void makeChildren () {
        String[] firstname = {"John-James", "Jack-James", "Jannet-James", "Jane-James"};
        String[] lastName = {"Jones", "Johnson", "Jenkins", "Jordan"};
        for (int i = 0; i < 3; i++) {
            provider.addChild(firstname[i], lastName[i],firstname[i], lastName[i]);
            //   assertFalse(firstname[i] == lastName[i]);
        }
        assertTrue(true);
    }
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.wsbapp", appContext.getPackageName());
    }
}