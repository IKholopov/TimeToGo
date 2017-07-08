package com.team4.yamblz.timetogo;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class NotificationService extends IntentService {
    private static final String EXTRA_LON = "longitude";
    private static final String EXTRA_LAT = "latitude";

    private static final String SERVER_NAME = "TIME_TO_GO_SERVICE";

    private static final int REPEAT_INTERVAL = 1000*10;// 10 сек

    public NotificationService() {
        super(SERVER_NAME);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static Intent newIntent(Context context, double lon, double lat){
        Intent i = new Intent(context,NotificationService.class);

        i.putExtra(EXTRA_LON,lon);
        i.putExtra(EXTRA_LAT,lat);

        return i;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Calendar currentTime = Calendar.getInstance();



        Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

        mapIntent.setPackage("com.google.android.apps.maps");

        PendingIntent pi = PendingIntent.getActivity(this,0,mapIntent,0);

        Resources res = getResources();

        Notification notification = new NotificationCompat.Builder(this)
                .setTicker(res.getText(R.string.service_notif_title))
                .setContentText(res.getText(R.string.service_notif_title))
                .setSmallIcon(android.R.drawable.arrow_down_float)
                .setContentTitle("Title")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(0,notification);
    }

    public static void setServiceAlarm(Context context, boolean isOn){

        Intent i = NotificationService.newIntent(context,6,6);

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
}
