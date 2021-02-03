package com.ilyo.shareideas;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.List;

public class NoteActivity extends AppCompatActivity {

    private static final String TAG = NoteActivity.class.getSimpleName();
    private static final String MY_CHANNEL_ID = "com.ilyo.shareideas.My_Notification";
    public static final int NOTIFICATION_ID = 0;

    private NoteInfo selectedNote;
    private boolean isNewNote;
    private Spinner spinnerCourses;
    private EditText textNoteTitle;
    private EditText textNoteContent;
    private int notePosition;
    private boolean isCancelling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        spinnerCourses = findViewById(R.id.spinner_courses);

        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        ArrayAdapter<CourseInfo> adapterCourses =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourses.setAdapter(adapterCourses);

        readDisplayStateValues();

        textNoteTitle = findViewById(R.id.editText_title);
        textNoteContent = findViewById(R.id.text_note_content);

        if (!isNewNote)
            displayNote(spinnerCourses, textNoteTitle, textNoteContent);

        Log.d(TAG, "onCreate");
    }

    private void displayNote(Spinner spinnerCourses, EditText textNoteTitle, EditText textNoteContent) {
        // Get the list of courses
        List<CourseInfo> courseInfoList = DataManager.getInstance().getCourses();
        int courseIndex = courseInfoList.indexOf(selectedNote.getCourse());
        spinnerCourses.setSelection(courseIndex);
        textNoteTitle.setText(selectedNote.getTitle());
        textNoteContent.setText(selectedNote.getText());
    }

    private void readDisplayStateValues() {
        Intent intent = getIntent();
        notePosition = intent.getIntExtra(NoteListActivity.NOTE_POSITION, -1);
        isNewNote = notePosition == -1;

        if (isNewNote) {
            createNewNote();
        }

        Log.i(TAG, "notePosition: " + notePosition);
        selectedNote = DataManager.getInstance().getNotes().get(notePosition);
    }

    private void createNewNote() {
        DataManager dm = DataManager.getInstance();
        notePosition = dm.createNewNote();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the actionbar if it's present
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_send_mail) {
            sendEmail();
            return true;
        } else if (id == R.id.action_cancel) {
            isCancelling = true;
            finish();
        } else if (id == R.id.action_next) {
            moveNext();
        } else if (id == R.id.action_set_reminder) {
            showReminderNotification();
        }

        return super.onOptionsItemSelected(item);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My channel Name :p";
            NotificationChannel channel = new NotificationChannel(MY_CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("My channel description: Android system");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showReminderNotification() {
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MY_CHANNEL_ID)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.like_notif))
                .setSmallIcon(R.drawable.ic_stat_note_reminder)
                .setContentTitle(textNoteTitle.getText().toString())
                .setContentText(textNoteContent.getText().toString())
                .setTicker("Review note ticker")
                // To support devices running android 7.1 and lower: Priority
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // To support devices running android 7.1 and lower: LED, Sound and vibration
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                // Set BigTextStyle to display more text
                .setStyle(new NotificationCompat.BigTextStyle()
                        .setBigContentTitle(textNoteTitle.getText().toString())
                        .bigText(textNoteContent.getText().toString())
                        .setSummaryText("Review Note!"))
                // Associate the pendingIntent so we can launch any activity from the notification
                .setContentIntent(buildIntent())
                // Set additional actions using pending Intent
                .addAction(R.drawable.ic_text, // Starting from Android 7.0, icons are not displayed in notifications
                        "View all notes",
                        PendingIntent.getActivity(this, NOTIFICATION_ID, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT))
                // Setting auto-cancel to true closes the notification when user taps on it.
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private PendingIntent buildIntent() {
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra(NoteListActivity.NOTE_POSITION, notePosition);

        return PendingIntent.getActivity(this,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    // initially called before the menu is displayed + it's triggered by calling invalidateOptionsMenu method
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_next);
        item.setEnabled(notePosition < DataManager.getInstance().getNotes().size() - 1);
        return super.onPrepareOptionsMenu(menu);
    }

    private void moveNext() {
        selectedNote = DataManager.getInstance().getNotes().get(++notePosition);
        displayNote(spinnerCourses, textNoteTitle, textNoteContent);
        // it will trigger onPrepareOptionsMenu method
        invalidateOptionsMenu();
    }

    private void sendEmail() {
        CourseInfo course = (CourseInfo) spinnerCourses.getSelectedItem();
        String subject = textNoteTitle.getText().toString();
        String content = textNoteContent.getText().toString() + "\n" + course.getTitle() + "\n\nBest regards,\n" + ":)";

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc2822");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        startActivity(intent);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (isCancelling) {
            Log.i(TAG, "Cancelling note at position: " + notePosition);
            if (isNewNote) {
                DataManager.getInstance().removeNote(notePosition);
            }
        } else {
            saveNote();
        }
        Log.d(TAG, "onPause");
    }

    private void saveNote() {
        Log.d(TAG, "note title is: " + textNoteTitle.getText().toString());

        selectedNote.setCourse((CourseInfo) spinnerCourses.getSelectedItem());
        selectedNote.setTitle(textNoteTitle.getText().toString());
        selectedNote.setText(textNoteContent.getText().toString());
    }


}
