package com.player.tubemotivational.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.player.tubemotivational.R;
import com.player.tubemotivational.user.interfaces.UserItemClickListener;
import com.player.tubemotivational.user.model.UserModel;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<UserModel> list;
    private Context ctx;
    private UserItemClickListener listener;

    public UserAdapter(List<UserModel> list, Context ctx, UserItemClickListener listener) {
        this.list = list;
        this.ctx = ctx;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_type_item_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserModel model = list.get(position);
        holder.name.setText(model.getUserName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        FrameLayout frameShare;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            frameShare = itemView.findViewById(R.id.frameShare);
            name = itemView.findViewById(R.id.title);
            frameShare.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.frameShare){
                Toast.makeText(ctx, "frameShare", Toast.LENGTH_SHORT).show();
            }
            listener.userItemClick(list.get(getAdapterPosition()).getUserName());
        }
    }
}
