package com.team4.yamblz.timetogo.data.database;

import android.provider.BaseColumns;

import java.util.Arrays;
import java.util.List;

/**
 * Created by AleksanderSh on 08.07.2017.
 */

public class TimeToGoDbSchema {
    public static final String DELIMITER = "/";

    public static String joinList(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String item : list) {
            builder.append(item);
        }
        return builder.toString();
    }

    public static List<String> parseToList(String string) {
        return Arrays.asList(string.split(DELIMITER));
    }

    public static final class ScheduleTable {
        public static final String NAME = "schedule";

        public static final class Cols implements BaseColumns {
            public static final String TITLE = "title";
            public static final String LOCATION = "location";
            public static final String DURATION = "duration";
            public static final String SCHOOLS = "schools";
            public static final String TEACHERS = "teachers";
            public static final String TIME = "time";
            public static final String ACCEPTED = "accepted";
        }
    }
}
