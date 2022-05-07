package com.example.chat_2022_eleves;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChoixConvActivity extends AppCompatActivity implements View.OnClickListener{

    GlobalState gs;
    public Spinner spinConversations;
    public Button btnChoixConv;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnChoixConv:
                gs.alerter("click OK");

                //Log.i(gs.CAT, String.valueOf(spinConversations.get));


                break;

        }
    }

    public class MyCustomAdapter extends ArrayAdapter<Conversation> {
        private int layoutId;
        private ArrayList<Conversation> dataConvs;

        public MyCustomAdapter(Context context,
                               int itemLayoutId,
                               ArrayList<Conversation> objects) {
            super(context, itemLayoutId, objects);
            layoutId = itemLayoutId;
            dataConvs = objects;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            //return getCustomView(position, convertView, parent);
            // getLayoutInflater() vient de Android.Activity => il faut utiliser une classe interne
            LayoutInflater inflater = getLayoutInflater();
            View item = inflater.inflate(layoutId, parent, false);
            Conversation nextC = dataConvs.get(position);

            TextView label = (TextView) item.findViewById(R.id.spinner_theme);
            label.setText(nextC.getTheme());

            ImageView icon = (ImageView) item.findViewById(R.id.spinner_icon);
            if (nextC.getActive()) icon.setImageResource(R.drawable.icon36);
            else icon.setImageResource(R.drawable.icongray36);

            return item;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //return getCustomView(position, convertView, parent);
            LayoutInflater inflater = getLayoutInflater();
            View item = inflater.inflate(layoutId, parent, false);
            Conversation nextC = dataConvs.get(position);

            TextView label = (TextView) item.findViewById(R.id.spinner_theme);
            label.setText(nextC.getTheme());

            ImageView icon = (ImageView) item.findViewById(R.id.spinner_icon);
            if (nextC.getActive()) icon.setImageResource(R.drawable.icon);
            else icon.setImageResource(R.drawable.icongray);
            return item;
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_conversation);
        btnChoixConv = findViewById(R.id.btnChoixConv);


        btnChoixConv.setOnClickListener(this);
        gs = (GlobalState) getApplication();
        Bundle bdl = this.getIntent().getExtras();
        gs.alerter("hash : " + bdl.getString("hash"));
        String hash = bdl.getString("hash");



        APIInterface apiService = APIClient.getClient().create(APIInterface.class);

        Call<ListConversations> call1 = apiService.doGetListConversation(hash);
        call1.enqueue(new Callback<ListConversations>() {
            @Override
            public void onResponse(Call<ListConversations> call, Response<ListConversations> response) {
                ListConversations listeConvs = response.body();
                Log.i(gs.CAT,listeConvs.toString());
                spinConversations = (Spinner) findViewById(R.id.spinConversations);
                //ArrayAdapter<Conversation> dataAdapter = new ArrayAdapter<Conversation>(
                //        ChoixConvActivity.this,
                //        android.R.layout.simple_spinner_item,
                //        listeConvs.getConversations());
                //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //spinConversations.setAdapter(dataAdapter);
                spinConversations.setAdapter(new MyCustomAdapter(ChoixConvActivity.this,
                        R.layout.spinner_item,
                        listeConvs.getConversations()));

                btnChoixConv.setEnabled(true);
            }

            @Override
            public void onFailure(Call<ListConversations> call, Throwable t) {
                call.cancel();
                btnChoixConv.setEnabled(false);
            }
        });
    }
}
