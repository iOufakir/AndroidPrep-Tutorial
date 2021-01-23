package com.ilyo.shareideas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * Created by iLyas Dev on 12/08/2020
 */
public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.MyViewHolder> {

    private final Context mContext;
    private final List<CourseInfo> coursesList;
    private final LayoutInflater layoutInflater;

    public CourseRecyclerAdapter(Context mContext, List<CourseInfo> coursesList) {
        this.mContext = mContext;
        this.coursesList = coursesList;

        layoutInflater = LayoutInflater.from(this.mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_row_course_list, parent, false);
        return new MyViewHolder(itemView);
    }

    // bind data with the recyclerview item views
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CourseInfo course = coursesList.get(position);
        holder.textCourse.setText(course.getTitle());

        holder.currentPosition = position;
    }

    @Override
    public int getItemCount() {
        return coursesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        final TextView textCourse;
        int currentPosition;

        public MyViewHolder(View itemView) {
            super(itemView);
            textCourse = itemView.findViewById(R.id.text_course);

            itemView.setOnClickListener(view -> Snackbar.make(view, coursesList.get(currentPosition).getTitle(), Snackbar.LENGTH_LONG).show());
        }
    }
    
}
