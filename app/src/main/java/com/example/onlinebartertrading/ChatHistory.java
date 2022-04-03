package com.example.onlinebartertrading;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ChatHistory extends RecyclerView.Adapter<ChatHistory.MyViewHolder>{

    Context context;

    ArrayList<ChatUser> list;

    public ChatHistory(Context context, ArrayList<ChatUser> list){
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.user,parent,false);
        return new MyViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatUser user = list.get(position);
        holder.first_name.setText(user.getFirst_name());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView first_name;
        Button button;


        public MyViewHolder(@NonNull View itemView, Context c) {
            super(itemView);
            first_name = itemView.findViewById(R.id.name);
            button = itemView.findViewById(R.id.button2);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(c, ChatActivity.class);
                    c.startActivity(intent);
                }
            });


        }
    }



}