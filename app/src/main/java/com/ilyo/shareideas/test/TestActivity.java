package com.ilyo.shareideas.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.ilyo.shareideas.R;

import java.util.Arrays;
import java.util.List;

public class TestActivity extends AppCompatActivity {

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

        // Notifications
        


    }

}