package com.moutamid.misscaddie;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class CalenderItemAdapter extends RecyclerView.Adapter<CalenderItemAdapter.ItemViewHolder> {

    Context context;
    ArrayList<String> daysOfMonth;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public CalenderItemAdapter(Context context, ArrayList<String> daysOfMonth) {
        this.context = context;
        this.daysOfMonth = daysOfMonth;
    }

    public CalenderItemAdapter(ArrayList<String> daysOfMonth) {
        this.daysOfMonth = daysOfMonth;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_items, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666);
        return new CalenderItemAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.day.setText(daysOfMonth.get(position));

        /*holder.itemView.setOnClickListener(v -> {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.yellow));
            holder.day.setTextColor(Color.WHITE);
        });*/
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        MaterialCardView cardView;
        TextView day;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.item_card);
            day = itemView.findViewById(R.id.calender_date);

        }
    }
}
