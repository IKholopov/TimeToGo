package com.team4.yamblz.timetogo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.team4.yamblz.timetogo.data.MapParser;
import com.team4.yamblz.timetogo.data.MapParserImpl;
import com.team4.yamblz.timetogo.data.RouteMode;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.team4.yamblz.timetogo", appContext.getPackageName());
    }

    @Test
    public void mapParser() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        MapParser parser = new MapParserImpl(appContext);
        Calendar arrival = parser.GetTimeToEvent(42.204321,-77.1124577, RouteMode.CAR);
        //55.7334551, 37.5869947
        Calendar now = Calendar.getInstance();
        long diff = arrival.getTimeInMillis() - now.getTimeInMillis();
        assertEquals(true, (diff - 390*1000) < 20*1000);
    }
}
