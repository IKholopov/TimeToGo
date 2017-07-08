package com.team4.yamblz.timetogo.data;

import com.team4.yamblz.timetogo.data.model.MobilizationBotData;

/**
 * Created by igor on 7/8/17.
 */

public interface BotDataParser {
    /**
     * Перевод json в объекты java.
     *
     * @param json Json-текст
     * @return Собранный из json объект с данными
     */
    MobilizationBotData fromJson(String json);
}
