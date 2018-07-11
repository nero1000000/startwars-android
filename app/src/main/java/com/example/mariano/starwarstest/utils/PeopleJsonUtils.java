package com.example.mariano.starwarstest.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nero1000000 on 11/07/18.
 */

public class PeopleJsonUtils {

    public static String[] getPeopleStringsFromJson(Context context, String peopleJsonStr)
            throws JSONException {
        String[] parsedPeopleData = null;

        JSONObject peopleJson = new JSONObject(peopleJsonStr);

        String peopleName = peopleJson.getString("name");
        String peopleGender = peopleJson.getString("gender");

        parsedPeopleData = new String[1];

        parsedPeopleData[0] = peopleName;

        return parsedPeopleData;
    }
}
