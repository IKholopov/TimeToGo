package com.team4.yamblz.timetogo.data;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;

import com.team4.yamblz.timetogo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by igor on 7/8/17.
 */

public class MapParserImpl implements MapParser {

    private final String TAG = "MapParserImpl";

    private Context context;

    private static class RequestParams {
        double latitude;
        double longitude;
        RouteMode mode;

        RequestParams(double latitude, double longitude, RouteMode mode) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.mode = mode;
        }
    }

    private String loadJsonString(RequestParams params) {
        final String API_KEY = "key";
        final String COORDS_ORIGIN_KEY = "origin";
        final String COORDS_DESTINATION_KEY = "destination";
        final String MODE_KEY= "mode";

        Uri builtUri = Uri.parse(context.getString(R.string.google_maps_url)).buildUpon()
                .appendQueryParameter(COORDS_ORIGIN_KEY, String.format("%f,%f", params.latitude, params.longitude))
                .appendQueryParameter(COORDS_DESTINATION_KEY, context.getString(R.string.default_destination_coords))
                .appendQueryParameter(MODE_KEY, context.getString(params.mode == RouteMode.CAR ?
                        R.string.mode_car : R.string.mode_public))
                .build();
        try {
            URL url = new URL(builtUri.toString());
            HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            String jsonString = buffer.toString();
            return jsonString;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public MapParserImpl(Context context) {
        this.context = context;
    }

    @Override
    public Calendar GetTimeToEvent(double latitude, double longitude, RouteMode mode){
        try {
            final String ROUTES_TAG  = "routes";
            final String LEGS_TAG  = "legs";
            final String DURATION_TAG = "duration";
            final String DURATION_VALUE_TAG = "value";
            String json = loadJsonString(new RequestParams(latitude, longitude, mode));
            if(json == null) {
                Log.e(TAG, "failed to connect");
                return null;
            }
            JSONObject root = new JSONObject(json);
            JSONArray routes = root.getJSONArray(ROUTES_TAG);
            int duration = routes.getJSONObject(0).getJSONArray(LEGS_TAG).getJSONObject(0)
                    .getJSONObject(DURATION_TAG).getInt(DURATION_VALUE_TAG);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(calendar.getTimeInMillis() + duration * 1000);
            return calendar;
        } catch (JSONException e) {
            Log.e(TAG, "JSON Parse error");
            return null;
        }
    }
}
