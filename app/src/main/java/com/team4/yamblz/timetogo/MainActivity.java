package com.team4.yamblz.timetogo;

import android.app.Activity;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.widget.Toast;

import com.team4.yamblz.timetogo.data.BotDataAssetReader;
import com.team4.yamblz.timetogo.data.BotDataParserImpl;
import com.team4.yamblz.timetogo.data.MapParser;
import com.team4.yamblz.timetogo.data.model.MobilizationBotData;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    public static MainActivity activity;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobilizationBotData data = new BotDataParserImpl().fromJson(new BotDataAssetReader(this).getText());
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null) {
            position = savedInstanceState.getInt("position", 0);
        }else{
            position = 0;
        }
        activity = this;
        NotificationService.setServiceAlarm(this,true);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                1);

        FragmentManager fm = getSupportFragmentManager();
        fm.addOnBackStackChangedListener(this);
        if(savedInstanceState == null) {
            fm.beginTransaction()
                    .add(R.id.fragment_container, ScheduleFragment.newInstance(position))
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    public void shouldDisplayHomeUp(){
        //Enable Up button only  if there are entries in the back stack
        boolean canback = getSupportFragmentManager().getBackStackEntryCount()>0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. Just the pop back stack.
        getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your Location", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt("position", position);
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
