package com.example.onlinebartertrading;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinebartertrading.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    public static final int left_message = 0;
    public static final int right_message = 1;

    private List<ChatList> chat;
    private Context context;
    private String receiver;

    User firebaseUser;

    public ChatAdapter(List<ChatList> users, Context context) {
        this.chat = users;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == right_message) {
            View view = LayoutInflater.from(context).inflate(R.layout.sent_message, parent, false);
            return new MyViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.received_message, parent, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {

        ChatList currentChat = chat.get(position);
        receiver = currentChat.getReceiver();
        holder.message.setText(currentChat.getMessage());
    }

    @Override
    public int getItemCount() {
        return chat.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView message;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.message);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (chat.get(position).getSender().equals(firebaseUser.getUuid())) {
            return right_message;
        } else {
            return left_message;
        }
    }

    public String getReceiver() {
        return receiver;
    }
}
