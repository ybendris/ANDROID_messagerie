package com.example.chat_2022_eleves;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

    private static final String CAT = "LE4-SI";
    APIInterface apiService;
    String hash;
    ListConversations lc;
    int idItemSelected = Integer.MAX_VALUE;

    @ViewById(R.id.dropdown_text)
    AutoCompleteTextView dropdownText;

    @AfterViews
    void initialize() {
        Bundle bdl = this.getIntent().getExtras();
        Log.i(CAT,bdl.getString("hash"));
        hash = bdl.getString("hash");

        apiService = APIClient.getClient().create(APIInterface.class);
        Call<ListConversations> call1 = apiService.doGetListConversation(hash);
        doInBackground(call1);
    }

    @Background
    void doInBackground(Call<ListConversations> call1) {
        call1.enqueue(new Callback<ListConversations>() {
            @Override
            public void onResponse(@NotNull Call<ListConversations> call, @NotNull Response<ListConversations> response) {
                lc = response.body();
                List<String> spinnerArray =  new ArrayList<>();
                List<Integer> idArray = new ArrayList<>();
                for(Conversation c : lc.conversations) {
                    spinnerArray.add(c.theme);
                    idArray.add(Integer.parseInt(c.id));
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
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                alerter("ID ITEM SELECTED " + Integer.toString(idArray.get(arg2 + 1)));
                idItemSelected = idArray.get(arg2);
            }
        });
        Log.i(CAT,lc.toString());
    }

    @Click
    void buttonChoixOKMD() {
        alerter("Click sur OK Conv");
        if(idItemSelected == Integer.MAX_VALUE){
            dropdownText.setError("Veuillez sélectionner une conversation",null);
            alerter("Veuillez sélectionner une conversation");
        }else{
            Intent change2Conv = new Intent(this,ConvActivity_.class);
            Bundle bdl = new Bundle();
            bdl.putString("conv", Integer.toString(idItemSelected));
            bdl.putString("hash", hash);
            change2Conv.putExtras(bdl);
            startActivity(change2Conv);
        }
    }

    private void alerter(String s) {
        Log.i(CAT,s);
        Toast t = Toast.makeText(this,s,Toast.LENGTH_SHORT);
        t.show();
    }
}