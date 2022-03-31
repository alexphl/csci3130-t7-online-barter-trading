package com.example.onlinebartertrading;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private List<ChatList> totalChatLists;
    private final Context context;

    public ChatAdapter(List<ChatList> totalChatLists, Context context) {
        this.totalChatLists = totalChatLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chat, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {

        ChatList list2 = totalChatLists.get(position);

        holder.firstName.setText(list2.getFirstName());
        holder.lastName.setText(list2.getLastName());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("first_name", list2.getFirstName());
                intent.putExtra("last_name", list2.getLastName());
                intent.putExtra("chat_key", list2.getChatKey());
                intent.putExtra("user_key", list2.getUserKey());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView firstName;
        private TextView lastName;
        private TextView email;
        private LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.background);
        }
    }

    public void updateData(List<ChatList> totalChatLists) {
        this.totalChatLists = totalChatLists;
        notifyDataSetChanged();
    }
}
