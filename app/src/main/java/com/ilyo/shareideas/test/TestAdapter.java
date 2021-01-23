package com.ilyo.shareideas.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ilyo.shareideas.R;

import java.util.List;

/**
 * Created by iLyas Dev on 09/01/2021
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyViewHolder> {

    private List<String> listItems;
    private Context context;

    public TestAdapter(final Context context, final List<String> listItems) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.test_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.titleView.setText( listItems.get(position) );

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView titleView;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.textViewTest);
        }

    }

}
