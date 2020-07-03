package com.example.lesson4;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {

    private ArrayList<WeatherData> dataList = new ArrayList<>();

    public void setData(List<WeatherData> list) {
        dataList.clear();
        dataList.addAll(list);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {

        private TextView text1;
        private TextView text2;
        private TextView text3;
        private TextView text4;

        public SimpleViewHolder(@NonNull View itemView) {
            super(itemView);

            text1 = itemView.findViewById(R.id.empty);
            text2 = itemView.findViewById(R.id.textDay);
            text3 = itemView.findViewById(R.id.textTempDay);
            text4 = itemView.findViewById(R.id.textTempNight);

        }

        public void bind(WeatherData item) {
            text1.setText(item.empty);
            text2.setText(item.day);
            text3.setText(item.tempDay);
            text4.setText(item.tempNight);

        }
    }
}
