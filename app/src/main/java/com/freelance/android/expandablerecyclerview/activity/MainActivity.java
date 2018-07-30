package com.freelance.android.expandablerecyclerview.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.freelance.android.expandablerecyclerview.R;
import com.freelance.android.expandablerecyclerview.adapter.HeroAdapter;
import com.freelance.android.expandablerecyclerview.model.Hero;
import com.freelance.android.expandablerecyclerview.services.APIService;
import com.freelance.android.expandablerecyclerview.services.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();

    public static final String Base_url = "https://simplifiedcoding.net/";
    List<Hero> mHeroList;
    APIService service;
    private RecyclerView mRecyclerView;
    private HeroAdapter mAdapter;

    TextView mEmptyStateTextView;
    View loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "TEST: onCreate() is called...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingIndicator = this.findViewById(R.id.pbLoadingIndicator);
        mEmptyStateTextView = this.findViewById(R.id.tvEmpty);

        loadHeroes();
    }

    private void loadHeroes() {
        Log.i(LOG_TAG, "TEST: loadHeroes() is called...");

        mRecyclerView = this.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mHeroList = new ArrayList<>();

        service = RetrofitClient.getClient(Base_url).create(APIService.class);
        service.getMarvelHero()
                .enqueue(new Callback<List<Hero>>() {
                    @Override
                    public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                        Log.i(LOG_TAG, "TEST: onResponse() called...");

                        try {
                            if (response.isSuccessful()) {
                                mHeroList = response.body();

                                mAdapter = new HeroAdapter(getApplicationContext(), mHeroList);
                                mRecyclerView.setAdapter(mAdapter);
                                loadingIndicator.setVisibility(View.INVISIBLE);

                                if (mEmptyStateTextView != null) {
                                    mEmptyStateTextView.setVisibility(View.INVISIBLE);
                                }
                            }

                        } catch (IndexOutOfBoundsException e) {
                            Log.e(LOG_TAG, e.toString());

                            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Hero>> call, Throwable t) {
                        Log.i(LOG_TAG, "TEST: onFailure() called...");

                        loader();
                    }
                });
    }

    private void loader() {
        Log.i(LOG_TAG, "TEST: loader() called...");

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {

            mEmptyStateTextView.setText(R.string.empty_textview);
            loadingIndicator.setVisibility(View.INVISIBLE);
        } else {

            mEmptyStateTextView.setText(R.string.noIC_textview);
            loadingIndicator.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onResume() {
        Log.i(LOG_TAG, "TEST: onResume() called...");

        super.onResume();
        loadHeroes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(LOG_TAG, "TEST: onCreateOptionsMenu() called...");

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(LOG_TAG, "TEST: onOptionsItemSelected() called...");

        int id = item.getItemId();

        if (id == R.id.action_reload) {
            if (isOnline() == false && mHeroList != null) {
                mEmptyStateTextView.setVisibility(View.VISIBLE);
                this.mHeroList.clear();
                mAdapter.notifyDataSetChanged();
            } else {
                loadHeroes();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isOnline() {
        Log.i(LOG_TAG, "TEST: isOnline() called...");

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {

            return true;
        } else {
            //no internet connection
            return false;
        }
    }

}
