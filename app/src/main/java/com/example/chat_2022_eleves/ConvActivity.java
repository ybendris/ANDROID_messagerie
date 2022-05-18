package com.example.chat_2022_eleves;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chat_2022_eleves.api.APIClient;
import com.example.chat_2022_eleves.api.APIInterface;
import com.example.chat_2022_eleves.object.ListMessages;
import com.example.chat_2022_eleves.object.Message;
import com.google.android.material.textfield.TextInputLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.activity_conversation)
public class ConvActivity extends AppCompatActivity {

    public GlobalState gs;

    APIInterface apiService;
    String hash;
    int conversationId;

    @ViewById(R.id.messageRecyclerView)
    RecyclerView messageRecyclerView;


    ListMessages messages;
    private MessagesAdapter messagesAdapter;

    @AfterViews
    void initialize() {
        Bundle bdl = this.getIntent().getExtras();
        Log.i(gs.CAT,bdl.getString("hash"));
        hash = bdl.getString("hash");
        conversationId = Integer.parseInt(bdl.getString("conversationId"));

        apiService = APIClient.getClient().create(APIInterface.class);
        Call<ListMessages> call1 = apiService.doGetListMessage(hash,conversationId);
        doInBackground(call1);
    }

    @Background
    void doInBackground(Call<ListMessages> call1) {
        call1.enqueue(new Callback<ListMessages>() {
            @Override

            public void onResponse(@NotNull Call<ListMessages> call, @NotNull Response<ListMessages> response) {
                messages = response.body();
                Log.i(gs.CAT,messages.toString());
                messagesAdapter = new MessagesAdapter(messages.getMessages());
                messageRecyclerView.setAdapter(messagesAdapter);
                messageRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                //onPostExecute(adapter, idArray);
            }

            @Override
            public void onFailure(@NotNull Call<ListMessages> call, @NotNull Throwable t) {
                call.cancel();
            }
        });
    }

    void init(){
        gs = (GlobalState) getApplication();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        init();
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.action_settings: gs.alerter("Préférences");

                // Changer d'activité pour afficher SettingsActivity
                Intent toSettings = new Intent(this,SettingsActivity.class);
                startActivity(toSettings);
                break;

            case R.id.action_account:
                gs.alerter("Compte");
                break;

        }
        return super.onOptionsItemSelected(item);
    }



}
