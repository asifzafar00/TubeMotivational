package com.player.tubemotivational.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.player.tubemotivational.R;
import com.player.tubemotivational.main.interfaces.ItemClickListener;
import com.player.tubemotivational.main.model.NameModel;

import java.util.List;

public class NameAdapter extends RecyclerView.Adapter<NameAdapter.MyNameViewHolder> {

    private List<NameModel> list;
    private Context ctx;
    private ItemClickListener listener;


    public NameAdapter(List<NameModel> list, Context ctx, ItemClickListener onClick) {
        this.listener = onClick;
        this.list = list;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MyNameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyNameViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.type_item_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyNameViewHolder holder, int position) {

        NameModel model = list.get(position);

        holder.name.setText(model.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyNameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;

        public MyNameViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.itemClick(list.get(getAdapterPosition()).getName());
        }
    }
}
