package com.example.chat_2022_eleves;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chat_2022_eleves.api.APIClient;
import com.example.chat_2022_eleves.api.APIInterface;
import com.example.chat_2022_eleves.object.Authentification;
import com.example.chat_2022_eleves.object.User;

import org.json.JSONException;
import org.json.JSONObject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.activity_compte)
public class CompteActivity extends AppCompatActivity {

    @ViewById(R.id.edtLogin)
    public EditText edtLogin;

    @ViewById(R.id.edtMdpCompte)
    public EditText edtPasse;

    @ViewById(R.id.edtMdpCompte2)
    public EditText edtPasse2;

    @ViewById(R.id.btnChangeMdp)
    public Button btnChangerMdp;

    public SharedPreferences sp;
    public GlobalState gs;
    public APIInterface apiService;
    User mdp;
    String hash;

    @AfterViews
    void init() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        gs = (GlobalState) getApplication();
        Bundle bdl = this.getIntent().getExtras();
        hash = bdl.getString("hash");
        apiService = APIClient.getClient().create(APIInterface.class);
    }

    @Click(R.id.btnChangeMdp)
    void onClickbtnChangerMdp(){
        gs.alerter("Changement ...");
        Log.i("IG2I",edtPasse.getText().toString() );
        Log.i("IG2I",edtPasse2.getText().toString() );
        String mdp1 = edtPasse.getText().toString();
        String mdp2 = edtPasse2.getText().toString();
        Log.i("IG2I",mdp1);
        Log.i("IG2I",mdp2);
        if(mdp1.equals(mdp2)){
            Call<User> call1 = apiService.doNewMDP(hash,edtPasse.getText().toString());
            doInBackground(call1);
        }
        else{
            gs.alerter("Les mots de passes ne sont pas identiques");
            return;
        }

    }

    @Background
    void doInBackground(Call<User> call1) {
        call1.enqueue(new Callback<User>(){

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (hash == "") return;
                Log.i("IG2I", String.valueOf(response.body()));
                gs.alerter("Mot de passe modifié");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                call.cancel();
            }
        });
    }

}
