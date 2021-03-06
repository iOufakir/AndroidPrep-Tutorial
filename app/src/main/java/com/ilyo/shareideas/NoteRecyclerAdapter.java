package com.ilyo.shareideas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by iLyas Dev on 12/08/2020
 */
public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.MyViewHolder> {

    private final Context mContext;
    private final List<NoteInfo> noteInfoList;
    private final LayoutInflater layoutInflater;

    public NoteRecyclerAdapter(Context mContext, List<NoteInfo> noteInfoList) {
        this.mContext = mContext;
        this.noteInfoList = noteInfoList;

        layoutInflater = LayoutInflater.from(this.mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_row_note_list, parent, false);
        return new MyViewHolder(itemView);
    }

    // bind data with the recyclerview item views
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NoteInfo noteInfo = noteInfoList.get(position);

        holder.textCourse.setText(noteInfo.getCourse().getTitle());
        holder.textTitle.setText(noteInfo.getTitle());

        holder.currentPosition = position;
    }

    @Override
    public int getItemCount() {
        return noteInfoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        final TextView textCourse;
        final TextView textTitle;
        int currentPosition;

        public MyViewHolder(View itemView) {
            super(itemView);
            textCourse = itemView.findViewById(R.id.text_course);
            textTitle = itemView.findViewById(R.id.text_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, NoteActivity.class);
                    intent.putExtra(NoteListActivity.NOTE_POSITION, currentPosition);
                    mContext.startActivity(intent);
                }
            });
        }
    }
    
}
