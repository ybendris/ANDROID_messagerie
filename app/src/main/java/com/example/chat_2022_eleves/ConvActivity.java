package com.example.chat_2022_eleves;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_conversation)
public class ConvActivity extends AppCompatActivity {

    public GlobalState gs;

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
