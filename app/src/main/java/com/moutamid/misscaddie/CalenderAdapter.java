package com.moutamid.misscaddie;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.CalenderViewHolder> {

    Context context;
    LocalDate selectedDate;
    ArrayList<String> month;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public CalenderAdapter(Context context, ArrayList<String> month) {
        this.context = context;
        this.month = month;
        selectedDate = LocalDate.now();
    }

    @NonNull
    @Override
    public CalenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.calender_layout, parent, false);
        return new CalenderViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CalenderViewHolder holder, int position) {
        holder.MonthYear.setText(month.get(position) + " " + selectedDate.getYear());
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalenderItemAdapter adapter = new CalenderItemAdapter(context, daysInMonth);
        holder.rc.setLayoutManager(new GridLayoutManager(context, 7));
        holder.rc.setHasFixedSize(false);
        holder.rc.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonth = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysMonth = yearMonth.lengthOfMonth();
        LocalDate firstofMonth = selectedDate.withDayOfMonth(1);
        int dayofWeek = firstofMonth.getDayOfWeek().getValue();

        for (int i=1; i<=42; i++){
            if (i <= dayofWeek || i > daysMonth + dayofWeek){
                daysInMonth.add("");
            } else {
                daysInMonth.add(String.valueOf(i - dayofWeek));
            }
        }
        return daysInMonth;
    }

    @Override
    public int getItemCount() {
        return month.size();
    }

    public class CalenderViewHolder extends RecyclerView.ViewHolder{
        TextView MonthYear;
        RecyclerView rc;
        public CalenderViewHolder(@NonNull View itemView) {
            super(itemView);
            MonthYear = itemView.findViewById(R.id.month_year);
            rc = itemView.findViewById(R.id.calendersDaysRV);
        }
    }
}
