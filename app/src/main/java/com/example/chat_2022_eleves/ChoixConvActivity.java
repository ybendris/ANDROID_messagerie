package com.example.chat_2022_eleves;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chat_2022_eleves.api.APIClient;
import com.example.chat_2022_eleves.api.APIInterface;
import com.example.chat_2022_eleves.object.Conversation;
import com.example.chat_2022_eleves.object.ListConversations;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@EActivity(R.layout.activity_choix_conversation)
public class ChoixConvActivity extends AppCompatActivity{
    APIInterface apiService;
    String hash;
    ListConversations conversations;
    int idItemSelected = Integer.MAX_VALUE;
    public GlobalState gs;

    @ViewById(R.id.dropdown_text)
    AutoCompleteTextView dropdownText;

    @AfterViews
    void initialize() {
        Bundle bdl = this.getIntent().getExtras();
        Log.i(gs.CAT,bdl.getString("hash"));
        hash = bdl.getString("hash");
        gs = (GlobalState) getApplication();

        apiService = APIClient.getClient().create(APIInterface.class);
        Call<ListConversations> call1 = apiService.doGetListConversation(hash);
        doInBackground(call1);
    }

    @Background
    void doInBackground(Call<ListConversations> call1) {
        call1.enqueue(new Callback<ListConversations>() {
            @Override

            public void onResponse(@NotNull Call<ListConversations> call, @NotNull Response<ListConversations> response) {
                conversations = response.body();
                List<String> spinnerArray =  new ArrayList<>();
                List<Integer> idArray = new ArrayList<>();
                for(Conversation c : conversations.getConversations()) {
                    spinnerArray.add(c.getTheme());
                    idArray.add(Integer.parseInt(c.getId()));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        ChoixConvActivity.this,
                        R.layout.dropdown_items,
                        spinnerArray
                );

                onPostExecute(adapter, idArray);
            }

            @Override
            public void onFailure(@NotNull Call<ListConversations> call, @NotNull Throwable t) {
                call.cancel();
            }
        });
    }

    @UiThread
    void onPostExecute(ArrayAdapter<String> adapter, List<Integer> idArray) {
        dropdownText.setAdapter(adapter);
        dropdownText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                gs.alerter("ID ITEM SELECTED " + Integer.toString(idArray.get(arg2)));
                idItemSelected = idArray.get(arg2);
            }
        });
        Log.i(gs.CAT, conversations.toString());
    }

    @Click
    void buttonChoixOKMD() {
        gs.alerter("Click sur OK Conv");
        if(idItemSelected == Integer.MAX_VALUE){
            dropdownText.setError("Veuillez sélectionner une conversation",null);
            gs.alerter("Veuillez sélectionner une conversation");
        }
        else{
            Intent change2Conv = new Intent(this,ConvActivity_.class);
            Bundle bdl = new Bundle();
            bdl.putString("conversationId", Integer.toString(idItemSelected));
            bdl.putString("hash", hash);
            change2Conv.putExtras(bdl);
            startActivity(change2Conv);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.action_settings:
                gs.alerter("Préférences");
                // Changer d'activité pour afficher SettingsActivity
                Intent toSettings = new Intent(this,SettingsActivity.class);
                startActivity(toSettings);
                break;

            case R.id.action_account:
                gs.alerter("Compte");
                Intent toAccount = new Intent(this,CompteActivity_.class);
                Bundle bdl = new Bundle();
                bdl.putString("hash", hash);
                toAccount.putExtras(bdl);
                startActivity(toAccount);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}