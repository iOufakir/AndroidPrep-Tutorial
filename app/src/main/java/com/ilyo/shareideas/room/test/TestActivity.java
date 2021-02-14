package com.ilyo.shareideas.room.test;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.ilyo.shareideas.R;
import com.ilyo.shareideas.room.Note;
import com.ilyo.shareideas.room.NoteViewModel;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = TestActivity.class.getName();

    private SharedPreferences mPreferences;
    private static final String sharedPrefFile = "com.ilyo.shareieads.MY_SHARED_PREFERENCE";
    private NoteViewModel noteViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // Initialize our model field and we will allow the android system to manage that state for us
        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(), // Where the viewModel will be stored
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())); // RefactoryClass that can create the ViewModels
        noteViewModel = viewModelProvider.get(NoteViewModel.class);

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
        final TestAdapter adapter = new TestAdapter(this);
        recyclerView.setAdapter(adapter);

        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground
        noteViewModel.getListNotes().observe(this, notes -> {
            Log.d(TAG, "Elements size : " + notes.size());
            adapter.setListItems(notes);
        });
        // Add a new data to the room DB
        final Button btnAddNote = findViewById(R.id.btn_add_note);
        final EditText editTextNoteTitle = findViewById(R.id.text_note_title);
        btnAddNote.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(editTextNoteTitle.getText())) {
                    noteViewModel.insert(new Note(editTextNoteTitle.getText().toString(), "Content TEST :D"));
            }else {
                Toast.makeText(this, "Value must not be empty!", Toast.LENGTH_LONG).show();
            }
        });


        // Preferences
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString("key1", "Test My preference 1");
        preferencesEditor.putBoolean("key for my boolean", true);
        preferencesEditor.apply();
        Log.d(TAG, "Preference values: " + mPreferences.getAll());
        Log.d(TAG, "Test: " + String.format("%s", mPreferences.getInt("key3", 0)));
        // Clear the preferences
        preferencesEditor.clear();
        preferencesEditor.apply();

        // WorkManager


    }

}