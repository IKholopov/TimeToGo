package com.team4.yamblz.timetogo.data.database.cursorWrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.team4.yamblz.timetogo.data.database.TimeToGoDbSchema;
import com.team4.yamblz.timetogo.data.database.TimeToGoDbSchema.ScheduleTable;
import com.team4.yamblz.timetogo.data.model.Schedule;

/**
 * Created by AleksanderSh on 08.07.2017.
 */

public class ScheduleCursorWrapper extends CursorWrapper {
    public ScheduleCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Schedule getSchedule() {
        Schedule schedule = new Schedule(getLong(getColumnIndex(ScheduleTable.Cols._ID)));
        schedule.setTitle(getString(getColumnIndex(ScheduleTable.Cols.TITLE)));
        schedule.setDuration(getDouble(getColumnIndex(ScheduleTable.Cols.DURATION)));
        schedule.setLocation(getString(getColumnIndex(ScheduleTable.Cols.LOCATION)));
        schedule.setTime(getString(getColumnIndex(ScheduleTable.Cols.TIME)));
        schedule.setAccepted(getLong(getColumnIndex(ScheduleTable.Cols.ACCEPTED)) == 1);
        schedule.setSchools(TimeToGoDbSchema.parseToList(
                getString(getColumnIndex(ScheduleTable.Cols.SCHOOLS))));
        schedule.setTeacher(TimeToGoDbSchema.parseToList(
                getString(getColumnIndex(ScheduleTable.Cols.TEACHERS))));

        return schedule;
    }
}