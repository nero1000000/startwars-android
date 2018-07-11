package com.example.mariano.starwarstest.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mariano.starwarstest.R;
import com.example.mariano.starwarstest.utils.NetworkHelper;
import com.example.mariano.starwarstest.utils.PeopleAdapter;
import com.example.mariano.starwarstest.utils.PeopleJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements PeopleAdapter.PeopleAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private PeopleAdapter mPeopleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.people_list);
        mErrorMessageDisplay = (TextView) findViewById(R.id.error_message);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mPeopleAdapter = new PeopleAdapter(this);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(mPeopleAdapter);

        showWeatherDataView();
        new FetchWeatherTask().execute("1");
    }

    private void showWeatherDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(String people) {
        Context context = this;
        Toast.makeText(context, people, Toast.LENGTH_SHORT)
                .show();
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String index = params[0];
            URL swApiRequestUrl = NetworkHelper.buildUrl(index);

            try {
                String jsonPeopleResponse = NetworkHelper
                        .getResponseFromHttpUrl(swApiRequestUrl);

                String[] simpleJsonPeopleData = PeopleJsonUtils
                        .getPeopleStringsFromJson(MainActivity.this, jsonPeopleResponse);

                return simpleJsonPeopleData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] peopleData) {
            if (peopleData != null) {
                showWeatherDataView();
                mPeopleAdapter.setPeopleData(peopleData);
            } else {
                showErrorMessage();
            }
        }
    }
}
