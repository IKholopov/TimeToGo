package com.team4.yamblz.timetogo.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.team4.yamblz.timetogo.data.BotDataAssetReader;
import com.team4.yamblz.timetogo.data.BotDataParserImpl;
import com.team4.yamblz.timetogo.data.model.MobilizationBotData;
import com.team4.yamblz.timetogo.data.model.Schedule;

import java.util.List;

/**
 * Created by AleksanderSh on 08.07.2017.
 */

public class TimeToGoDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "TimeToGoDbHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "timeToGoDatabase.db";

    private Context mContext;

    public TimeToGoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TimeToGoDbSchema.ScheduleTable.NAME + "(" +
                TimeToGoDbSchema.ScheduleTable.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TimeToGoDbSchema.ScheduleTable.Cols.TITLE + " TEXT, " +
                TimeToGoDbSchema.ScheduleTable.Cols.LOCATION + " TEXT, " +
                TimeToGoDbSchema.ScheduleTable.Cols.DURATION + " REAL, " +
                TimeToGoDbSchema.ScheduleTable.Cols.SCHOOLS + " TEXT, " +
                TimeToGoDbSchema.ScheduleTable.Cols.TEACHERS + " TEXT, " +
                TimeToGoDbSchema.ScheduleTable.Cols.ACCEPTED + " INTEGER, " +
                TimeToGoDbSchema.ScheduleTable.Cols.TIME + " TEXT" +
                ")"
        );

        fillDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void fillDatabase(SQLiteDatabase db) {
        String json = new BotDataAssetReader(mContext).getText();
        MobilizationBotData data = new BotDataParserImpl().fromJson(json);
        List<Schedule> schedule = data.getSchedule();
        for (Schedule event : schedule) {
            ContentValues values = new ContentValues();
            values.put(TimeToGoDbSchema.ScheduleTable.Cols.TITLE, event.getTitle());
            values.put(TimeToGoDbSchema.ScheduleTable.Cols.ACCEPTED, event.isAccepted());
            values.put(TimeToGoDbSchema.ScheduleTable.Cols.DURATION, event.getDuration());
            values.put(TimeToGoDbSchema.ScheduleTable.Cols.LOCATION, event.getLocation());
            values.put(TimeToGoDbSchema.ScheduleTable.Cols.TIME, event.getTime());
            values.put(TimeToGoDbSchema.ScheduleTable.Cols.SCHOOLS,
                    TimeToGoDbSchema.joinList(event.getSchools()));
            values.put(TimeToGoDbSchema.ScheduleTable.Cols.TEACHERS,
                    TimeToGoDbSchema.joinList(event.getTeacher()));

            db.insert(TimeToGoDbSchema.ScheduleTable.NAME, null, values);
        }
    }
}
