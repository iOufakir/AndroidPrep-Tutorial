package com.ilyo.shareideas.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.ilyo.shareideas.R;

import java.util.Arrays;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = TestActivity.class.getName();

    private SharedPreferences mPreferences;
    private static final String sharedPrefFile = "com.ilyo.shareieads.MY_SHARED_PREFERENCE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Button btnShowPopup = findViewById(R.id.btnShowPopup);
        // Toast
        btnShowPopup.setOnClickListener(v -> Toast
                .makeText(this, "Hello Ilyas, this is a toast message!", Toast.LENGTH_LONG)
                .show());

        // Snackbar
        Snackbar.make(findViewById(R.id.btnShowPopup), "Hello, I'm the snackbar ;)", Snackbar.LENGTH_LONG).show();

        // RecyclerView
        RecyclerView recyclerView = findViewById(R.id.listItemsTest);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        List<String> listDate = Arrays.asList("Test 1", "Test 2", "Test 3", "Test 4", "Test 5");
        recyclerView.setAdapter(new TestAdapter(this, listDate));

        // Preferences
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor =  mPreferences.edit();
        preferencesEditor.putString("key1", "Test My preference 1");
        preferencesEditor.putBoolean("key for my boolean", true);
        preferencesEditor.apply();
        Log.d(TAG, "Preference values: "+mPreferences.getAll());
        Log.d(TAG, "Test: "+String.format("%s", mPreferences.getInt("key3", 0)));
        // Clear the preferences
        preferencesEditor.clear();
        preferencesEditor.apply();


        // Notifications
        
        // WorkManager

    }

}