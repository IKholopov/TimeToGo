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
 * <p>
 * Класс для работы с базой данных.
 */

public class ScheduleDao {
    private static final String TAG = "ScheduleDao";
    private static ScheduleDao scheduleDao;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    /**
     * Класс сделан синглтоном, поэтому получение экземпляра через этот статический метод.
     *
     * @param context Контекст
     * @return Единственный экземпляр класса.
     */
    public static ScheduleDao get(Context context) {
        if (scheduleDao == null)
            scheduleDao = new ScheduleDao(context);
        return scheduleDao;
    }

    private ScheduleDao(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TimeToGoDbHelper(mContext).getWritableDatabase();
    }

    /**
     * Получение всего списка событий в базе данных.
     *
     * @return список событий.
     */
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

    /**
     * Получение события по его идентификатору.
     *
     * @param id Идентификатор события
     * @return Найденное событие, если событие в бд не найдено, возвращается {@code null}.
     */
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

    /**
     * Обновление события в базе данных.
     *
     * @param schedule Событие, которое следует обновить.
     */
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
