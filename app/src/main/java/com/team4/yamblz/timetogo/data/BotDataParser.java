package com.team4.yamblz.timetogo.data;

import com.team4.yamblz.timetogo.data.model.MobilizationBotData;

/**
 * Created by igor on 7/8/17.
 */

public interface BotDataParser {
    MobilizationBotData fromJson(String json);
}
