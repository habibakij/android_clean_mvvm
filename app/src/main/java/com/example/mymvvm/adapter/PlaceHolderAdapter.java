package com.example.mymvvm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymvvm.R;
import com.example.mymvvm.data.datasourse.local.PlaceHolderEntity;
import com.example.mymvvm.data.model.RecyclerItemClick;

import java.util.ArrayList;
import java.util.List;

public class PlaceHolderAdapter extends RecyclerView.Adapter<PlaceHolderAdapter.DataViewHolder> {
    Context mContext;
    private List<PlaceHolderEntity> placeHolderEntities = new ArrayList<>();
    private final RecyclerItemClick recyclerItemClick;
    int listItemPosition;

    public PlaceHolderAdapter(Context mContext, List<PlaceHolderEntity> placeHolderEntities, RecyclerItemClick recyclerItemClick, int listItemPosition) {
        this.mContext = mContext;
        this.placeHolderEntities = placeHolderEntities;
        this.recyclerItemClick = recyclerItemClick;
        this.listItemPosition = listItemPosition;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        PlaceHolderEntity response= placeHolderEntities.get(position);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItemPosition= position;
                recyclerItemClick.onItemClick(position);
                notifyDataSetChanged();
            }
        });
        if (listItemPosition == position){
            holder.linearLayout.setBackgroundColor(Color.CYAN);
        } else {
            holder.linearLayout.setBackgroundColor(Color.LTGRAY);
        }

        holder.data_set.setText("UID: "+response.getUID()+"\n"+"ID: "+response.getID()+"\n"+"User ID: "+response.getUserID()+"\n"+"Title: "+response.getTitle()+"\n"+"Status: "+response.getStatus());

    }

    @Override
    public int getItemCount() {
        Log.d("TAG", "check_adapter_list: "+placeHolderEntities.size());
        return placeHolderEntities.size();
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {
        private TextView data_set;
        LinearLayout linearLayout;
        DataViewHolder(View itemView) {
            super(itemView);
            data_set = itemView.findViewById(R.id.data_set);
            linearLayout = itemView.findViewById(R.id.layout_item);
        }
    }
}

