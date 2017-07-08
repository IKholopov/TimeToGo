package com.team4.yamblz.timetogo.data;

import com.google.gson.Gson;
import com.team4.yamblz.timetogo.data.model.MobilizationBotData;

/**
 * Created by AleksanderSh on 08.07.2017.
 */

public class BotDataParserImpl implements BotDataParser {
    @Override
    public MobilizationBotData fromJson(String json) {
        Gson gson = new Gson();
        MobilizationBotData data = gson.fromJson(json, MobilizationBotData.class);
        return data;
    }
}
