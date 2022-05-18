package com.example.chat_2022_eleves;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chat_2022_eleves.object.Message;

import org.androidannotations.annotations.ViewById;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {
    private List<Message> messages;

    public MessagesAdapter(List<Message> messages) {
        this.messages = messages;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.message, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.i("IG2I TEST",messages.get(position).toString());
        holder.display(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    public boolean addMessage(Message m){
        return messages.add(m);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView auteurTV;
        private TextView contenuTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            auteurTV = (TextView) itemView.findViewById(R.id.auteur_MaterialTextView);
            contenuTV = (TextView) itemView.findViewById(R.id.message_MaterialTextView);
        }

        void display(Message message){
            auteurTV.setText(message.getAuteur());
            contenuTV.setText(message.getContenu());
        }


        @Override
        public void onClick(View view) {

        }
    }
}
