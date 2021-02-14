package com.ilyo.shareideas.room.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ilyo.shareideas.R;
import com.ilyo.shareideas.room.Note;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * Created by iLyas Dev on 09/01/2021
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyViewHolder> {

    private List<Note> listItems;
    private final LayoutInflater mInflater;

    public TestAdapter(final Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.test_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.titleView.setText( listItems.get(position).getTitle() );
    }

    @Override
    public int getItemCount() {
        return Objects.isNull(listItems) ? 0 : listItems.size();
    }


    public void setListItems(List<Note> listItems) {
        this.listItems = listItems;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView titleView;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.textViewTest);
        }

    }

}
