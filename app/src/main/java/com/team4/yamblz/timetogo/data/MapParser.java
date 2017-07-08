package com.team4.yamblz.timetogo.data;

import org.json.JSONException;

import java.util.Calendar;

public interface MapParser {
    /*
     * Делает запрос (блокирующий) о времени, необходимого, чтобы добраться до события.
     * Возвращает либо календарь, указывающий на дату-время прибытия, либо null в случае ошибки
     */
    Calendar GetTimeToEvent(double latitude, double longitude, RouteMode mode) throws JSONException;
}
