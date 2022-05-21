package com.example.chat_2022_eleves;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.chat_2022_eleves.api.APIClient;
import com.example.chat_2022_eleves.api.APIInterface;
import com.example.chat_2022_eleves.object.Authentification;
import com.google.android.material.snackbar.Snackbar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public APIInterface apiService;
    Authentification auth;
    ConstraintLayout constraintLayout;


    @AfterViews
    void init() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        gs = (GlobalState) getApplication();
        constraintLayout=findViewById(R.id.Viewlogin);
        btnLogin.setEnabled(gs.verifReseau());
        apiService = APIClient.getClient().create(APIInterface.class);

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
        gs.alerter("Connexion", constraintLayout);
        Call<Authentification> call1 = apiService.doAuthenticate(edtLogin.getText().toString(),edtPasse.getText().toString());
        doInBackground(call1);
    }

    @Background
    void doInBackground(Call<Authentification> call1) {
        call1.enqueue(new Callback<Authentification>() {
            @Override
            public void onResponse(@NotNull Call<Authentification> call, @NotNull Response<Authentification> response) {
                auth = response.body();
                //TODO ne pas oublier en cas de problèmes de mdp
                if(auth == null) return;

                Log.i(gs.CAT,response.body().toString());

                Intent versChoixConv = new Intent(LoginActivity.this,ChoixConvActivity_.class);
                Bundle bdl = new Bundle();
                bdl.putString("hash",auth.getHash());

                versChoixConv.putExtras(bdl);
                startActivity(versChoixConv);
            }

            @Override
            public void onFailure(@NotNull Call<Authentification> call, @NotNull Throwable t) {
                call.cancel();
            }
        });
    }



    @Click(R.id.cbRemember)
    void onClickCbRemember(){
        SharedPreferences.Editor editor = sp.edit();
        gs.alerter("Identifiant et Mot de passe enregistrés", constraintLayout);

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


    
}