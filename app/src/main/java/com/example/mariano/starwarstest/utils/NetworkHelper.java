package com.example.mariano.starwarstest.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by nero1000000 on 10/07/18.
 */

public final class NetworkHelper {

    private static final String TAG = NetworkHelper.class.getSimpleName();

    private static final String SW_API_URL =
            "https://swapi.co/api/";
    private static final String PEOPLE_KEY =
            "people";

    public static URL buildUrl(String index) {
        Uri builtUri = Uri.parse(SW_API_URL).buildUpon()
                .appendPath(PEOPLE_KEY)
                .appendPath(index)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built URI " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
