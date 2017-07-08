package com.team4.yamblz.timetogo;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.team4.yamblz.timetogo.data.BotDataAssetReader;
import com.team4.yamblz.timetogo.data.BotDataParserImpl;
import com.team4.yamblz.timetogo.data.MapParserImpl;
import com.team4.yamblz.timetogo.data.RouteMode;
import com.team4.yamblz.timetogo.data.ScheduleDao;
import com.team4.yamblz.timetogo.data.model.MobilizationBotData;
import com.team4.yamblz.timetogo.data.model.Schedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotificationService extends IntentService {
    private static final String SERVICE_NAME = "TIME_TO_GO_SERVICE";

    private static final int REPEAT_INTERVAL = 1000 * 10;// 10 сек

    private AsyncLocator locator;

    public NotificationService() {
        super(SERVICE_NAME);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, NotificationService.class);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        final Location currentLocation;

        try {
            LocationServices.getFusedLocationProviderClient(this).getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                startLocator(location);
                            }
                        }
                    });
        }catch (SecurityException se){
            //Не вышло
        }
        catch (Exception e){
            //Не вышло
            return;
        }

    }

    public static void setServiceAlarm(Context context, boolean isOn){

        Intent i = NotificationService.newIntent(context);

        PendingIntent pi =PendingIntent.getService(context,0,i,0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if(isOn){
            alarmManager.setInexactRepeating(
                    AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),REPEAT_INTERVAL,pi);
        }else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }

    private void showNotification(Location location) throws java.text.ParseException{
        RouteMode mode;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String strMode = sharedPreferences.getString("route_mode",null);

        if(strMode == getString(R.string.route_mode_car)){
            mode = RouteMode.CAR;
        }else
        {
            mode = RouteMode.PUBLIC;
        }

        String school = sharedPreferences.getString("mobilization_school","");

        Date dateOfLecture = new Date();
        List<Schedule> schedules = ScheduleDao.get(this).getSchedules();

        //Поиск следующего занятия
        for(int i=0;i<schedules.size();i++){
            Schedule schedule = schedules.get(i);
            if(schedule.getSchools().contains(school)) {
                dateOfLecture = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ").parse(schedule.getTime());

                Date currentDate = new Date();

                if (currentDate.getTime() < dateOfLecture.getTime()) {
                    //И еще школа должна учитываться
                    break;
                }
                Log.d("Date", currentDate.toString());
                Log.d("Date", dateOfLecture.toString());

                Log.d("Time: ", schedule.getTime());
            }
        }


        Calendar timeToBe = new MapParserImpl(this)
                .GetTimeToEvent(location.getLatitude(),location.getLongitude(),mode);
        timeToBe.add(Calendar.MINUTE,10);

        Date dateToBe = timeToBe.getTime();


        if(dateToBe.getTime()>dateOfLecture.getTime() | true){
            String mapString = "google.navigation:q=55.7340273,37.5883457";
            if(mode == RouteMode.CAR){
                mapString+="&mode=d";
            }else if(mode == RouteMode.PUBLIC){
                mapString+="&mode=w";
            }

            Uri gmmIntentUri = Uri.parse(mapString);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

            mapIntent.setPackage("com.google.android.apps.maps");
            PendingIntent pi = PendingIntent.getActivity(this,0,mapIntent,0);
            Resources res = getResources();

            Notification notification = new NotificationCompat.Builder(this)
                    .setTicker(res.getText(R.string.service_notif_ticker))
                    .setContentText(res.getText(R.string.service_notif_ticker))
                    .setSmallIcon(android.R.drawable.ic_dialog_alert)
                    .setContentTitle(res.getText(R.string.service_notif_title))
                    .addAction(android.R.drawable.ic_dialog_map,res.getString(R.string.service_notif_open_map),pi)
                    .build();

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(0,notification);
        }
    }

    class AsyncLocator extends AsyncTask<Location,Void,Void> {
        @Override
        protected Void doInBackground(Location... params) {
            try{
                showNotification(params[0]);
            }catch (Exception e){

            }
            return null;
        }
    }

    void startLocator(Location location){
        if(locator!=null){
            locator.cancel(true);
        }
        locator = new AsyncLocator();
        locator.execute(location);
    }
}
