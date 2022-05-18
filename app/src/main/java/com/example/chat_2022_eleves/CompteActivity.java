package com.example.chat_2022_eleves;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

@EActivity(R.layout.activity_compte)
public class CompteActivity extends AppCompatActivity {

    @ViewById(R.id.edtLogin)
    public EditText edtLogin;

    @ViewById(R.id.edtMdpCompte)
    public EditText edtPasse;

    @ViewById(R.id.edtMdpCompte)
    public EditText edtPasse2;

    @ViewById(R.id.btnChangeMdp)
    public Button btnChangerMdp;

    public SharedPreferences sp;
    public GlobalState gs;


    @Click(R.id.btnChangeMdp)
    void onClickbtnChangerMdp(){
        gs.alerter("Changer mot de passe");
        //gs.requeteGET("http://tomnab.fr/fixture/","");
        //JSONAsyncTask reqGET = new JSONAsyncTask();
        //reqGET.execute("http://tomnab.fr/fixture/","cle=valeur");

        // http://tomnab.fr/chat-api/authenticate
        LoginActivity.PostAsyncTask reqPUT= new CompteActivity.PostAsyncTask();
        reqPUT.execute("http://tomnab.fr/chat-api/authenticate",
                "user=" + edtLogin.getText().toString()
                        + "&password=" + edtPasse.getText().toString());
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
            Intent versLogin = new Intent(CompteActivity.this,LoginActivity.class);
            Bundle bdl = new Bundle();
            bdl.putString("hash",hash);
            versLogin.putExtras(bdl);
            startActivity(versLogin);
        }

}
