package com.ilyo.shareideas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.ilyo.shareideas.test.TestActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private NoteRecyclerAdapter noteRecyclerAdapter;
    private RecyclerView recyclerItems;
    private CourseRecyclerAdapter courseRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivity(new Intent(this, NoteActivity.class)));
        fab = findViewById(R.id.fab2);
        fab.setOnClickListener(this::switchActivity);

        // Display default values from preferences
        PreferenceManager.setDefaultValues(this, R.xml.messages_preferences, false);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initializeDisplayContent();
    }

    private void switchActivity(View view){
        Intent intent = new Intent(MainActivity.this, TestActivity.class);
        startActivity(intent);
    }


    private void initializeDisplayContent() {
        recyclerItems = findViewById(R.id.list_items);

        // Initialize our adapters
        final List<NoteInfo> notes = DataManager.getInstance().getNotes();
        noteRecyclerAdapter = new NoteRecyclerAdapter(this, notes);

        final List<CourseInfo> courses = DataManager.getInstance().getCourses();
        courseRecyclerAdapter = new CourseRecyclerAdapter(this, courses);

        displayNotes();
    }

    // this can be called for the first time and also from menu drawer layout
    // so we need to call setAdapter method to update the recyclerView
    private void displayNotes(){
        final LinearLayoutManager notesLayoutManager = new LinearLayoutManager(this);
        recyclerItems.setLayoutManager(notesLayoutManager);
        recyclerItems.setAdapter(noteRecyclerAdapter);

        // make it checked by default for the first time (no interactions yet)
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_notes).setChecked(true);
    }

    private void displayCourses(){
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                getResources().getInteger(R.integer.course_recycler_grid_span));
        recyclerItems.setLayoutManager(gridLayoutManager);
        recyclerItems.setAdapter(courseRecyclerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // every time, activity is resumed, refresh our data set
        noteRecyclerAdapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_notes){
            displayNotes();
        }else if(id == R.id.nav_courses){
            displayCourses();
        }else if(id == R.id.nav_share){
            handleSelection(R.string.nav_share_message);
        }else if(id == R.id.nav_send){
            handleSelection(R.string.nav_send);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void handleSelection(int message) {
        // We just need any view who can pass to snackbar of the current activity
        // we choose the RecyclerView
        View view = findViewById(R.id.list_items);
        Snackbar.make(view, getString(message), Snackbar.LENGTH_LONG).show();
    }
}
