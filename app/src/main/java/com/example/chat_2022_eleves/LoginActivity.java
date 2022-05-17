package com.example.chat_2022_eleves;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    @ViewById(R.id.edtLogin)
    public EditText edtLogin;

    @ViewById(R.id.edtPasse)
    public EditText edtPasse;

    @ViewById(R.id.btnLogin)
    public Button btnLogin;

    @ViewById(R.id.cbRemember)
    public CheckBox cbRemember;

    public SharedPreferences sp;
    public GlobalState gs;


    @AfterViews
    void init() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        gs = (GlobalState) getApplication();
        btnLogin.setEnabled(gs.verifReseau());

        // relire les préférences de l'application
        // mettre à jour le formulaire
        if (sp.getBoolean("remember",false)) {
            // on charge automatiquement les champs login/passe
            // on coche la case
            edtLogin.setText(sp.getString("login",""));
            edtPasse.setText(sp.getString("passe",""));
            cbRemember.setChecked(true);
        }
        else {
            // on vide
            edtLogin.setText("");
            edtPasse.setText("");
            cbRemember.setChecked(false);
        }
    }

    @Click(R.id.btnLogin)
    void onClickBtnLogin(){
        gs.alerter("click OK");
        //gs.requeteGET("http://tomnab.fr/fixture/","");
        //JSONAsyncTask reqGET = new JSONAsyncTask();
        //reqGET.execute("http://tomnab.fr/fixture/","cle=valeur");

        // http://tomnab.fr/chat-api/authenticate
        PostAsyncTask reqPOST= new PostAsyncTask();
        reqPOST.execute("http://tomnab.fr/chat-api/authenticate",
                "user=" + edtLogin.getText().toString()
                        + "&password=" + edtPasse.getText().toString());
    }

    @Click(R.id.cbRemember)
    void onClickCbRemember(){
        SharedPreferences.Editor editor = sp.edit();
        gs.alerter("click Se souvenir de moi");
        if (cbRemember.isChecked()) {
            // on sauvegarde tout
            editor.putBoolean("remember", true);
            editor.putString("login",edtLogin.getText().toString());
            editor.putString("passe",edtPasse.getText().toString());
        } else {
            // on oublie tout
            editor.putBoolean("remember", false);
            editor.putString("login","");
            editor.putString("passe","");
        }
        editor.commit();
    }

    class PostAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... data) {
            String res = gs.requetePOST(data[0] ,data[1]);
            // {"version":1.3,"success":true,
            // "status":202,"hash":"efd18c70f94a580d9dc85533ddcd9823"}
            try {
                JSONObject ob = new JSONObject(res);
                return ob.getString("hash");
            } catch (JSONException e) {
                e.printStackTrace();
                return "";
            }
        }

        protected void onPostExecute(String hash) {
            // changer d'activité => ChoixConversations
            if (hash == "") return;
            Intent versChoixConv = new Intent(LoginActivity.this,ChoixConvActivity_.class);
            Bundle bdl = new Bundle();
            bdl.putString("hash",hash);
            versChoixConv.putExtras(bdl);
            startActivity(versChoixConv);
        }
    }


}