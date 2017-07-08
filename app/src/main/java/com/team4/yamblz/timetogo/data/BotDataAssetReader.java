package com.team4.yamblz.timetogo.data;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by AleksanderSh on 08.07.2017.
 */

public class BotDataAssetReader {
    private static final String BOT_DATA_FILE_PATH = "MobilizationBotData.txt";

    private Context mContext;

    public BotDataAssetReader(Context context) {
        mContext = context;
    }

    public String getText() {
        AssetManager assetManager = mContext.getAssets();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(assetManager.open(BOT_DATA_FILE_PATH)));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
