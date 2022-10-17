package com.moutamid.misscaddie.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.models.TableRowModel;

import java.util.List;

public class TableRowAdapter extends RecyclerView.Adapter<TableRowAdapter.VH> {

    List<TableRowModel> list;

    public TableRowAdapter(List<TableRowModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_row_layout, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        TableRowModel model = list.get(position);
        holder.messgae.setText(model.getMessage());
        /*if (list.size() < 3) {
            holder.messgae.setText(model.getMessage());
        } else holder.messgae.setText("more");*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        TextView messgae;

        public VH(@NonNull View itemView) {
            super(itemView);
            messgae = itemView.findViewById(R.id.table_message);
        }
    }
}
