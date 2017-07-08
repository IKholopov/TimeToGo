package com.team4.yamblz.timetogo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.team4.yamblz.timetogo.data.database.TimeToGoDbHelper;
import com.team4.yamblz.timetogo.data.database.TimeToGoDbSchema;
import com.team4.yamblz.timetogo.data.database.cursorWrapper.ScheduleCursorWrapper;
import com.team4.yamblz.timetogo.data.model.Schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AleksanderSh on 08.07.2017.
 */

public class ScheduleDao {
    private static final String TAG = "ScheduleDao";
    private static ScheduleDao scheduleDao;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ScheduleDao get(Context context) {
        if (scheduleDao == null)
            scheduleDao = new ScheduleDao(context);
        return scheduleDao;
    }

    private ScheduleDao(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TimeToGoDbHelper(mContext).getWritableDatabase();
    }

    public List<Schedule> getSchedules() {
        List<Schedule> schedules = new ArrayList<>();
        mDatabase.beginTransaction();
        try {
            ScheduleCursorWrapper cursor = getScheduleCursorByQuery(null, null);
            try {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    schedules.add(cursor.getSchedule());
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        return schedules;
    }

    public Schedule getScheduleById(long id) {
        Schedule schedule = null;
        String selection = TimeToGoDbSchema.ScheduleTable.Cols._ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        mDatabase.beginTransaction();
        try {
            ScheduleCursorWrapper cursor = getScheduleCursorByQuery(selection, selectionArgs);
            try {
                if (cursor.getCount() != 0) {
                    cursor.moveToFirst();
                    schedule = cursor.getSchedule();
                }
            } finally {
                cursor.close();
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        return schedule;
    }

    public void saveSchedule(Schedule schedule) {
        String selection = TimeToGoDbSchema.ScheduleTable.Cols._ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(schedule.getId())};
        mDatabase.beginTransaction();
        try {
            mDatabase.update(TimeToGoDbSchema.ScheduleTable.NAME,
                    getScheduleContentValues(schedule),
                    selection,
                    selectionArgs);
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    private ScheduleCursorWrapper getScheduleCursorByQuery(String selection, String[] selectionArgs) {
        ScheduleCursorWrapper cursor = new ScheduleCursorWrapper(
                mDatabase.query(
                        TimeToGoDbSchema.ScheduleTable.NAME,
                        null,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                ));
        return cursor;
    }

    private ContentValues getScheduleContentValues(Schedule schedule) {
        ContentValues values = new ContentValues();
        values.put(TimeToGoDbSchema.ScheduleTable.Cols.TITLE, schedule.getTitle());
        values.put(TimeToGoDbSchema.ScheduleTable.Cols.ACCEPTED, schedule.isAccepted());
        values.put(TimeToGoDbSchema.ScheduleTable.Cols.DURATION, schedule.getDuration());
        values.put(TimeToGoDbSchema.ScheduleTable.Cols.LOCATION, schedule.getLocation());
        values.put(TimeToGoDbSchema.ScheduleTable.Cols.TIME, schedule.getTime());
        values.put(TimeToGoDbSchema.ScheduleTable.Cols.SCHOOLS,
                TimeToGoDbSchema.joinList(schedule.getSchools()));
        values.put(TimeToGoDbSchema.ScheduleTable.Cols.TEACHERS,
                TimeToGoDbSchema.joinList(schedule.getTeacher()));

        return values;
    }
}
