package com.example.juego;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreActivity extends AppCompatActivity {

    TextView txtUserScore, txtNumberScore, txtAccuScore;

    public static SharedPreferences sharedPreferences;
    public static String NAME_FILE = "configuration";
    Integer acumulador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtUserScore = (TextView) findViewById(R.id.txtUserScore);
        txtNumberScore = (TextView) findViewById(R.id.txtNumberScore);
        txtAccuScore = (TextView) findViewById(R.id.txtAccuScore);
        sharedPreferences = getSharedPreferences(NAME_FILE, MODE_PRIVATE);
        String user = sharedPreferences.getString("USER", "");


        if ( user == null){
            Toast.makeText(ScoreActivity.this, "Login first.",Toast.LENGTH_SHORT).show();
        }else{
            txtUserScore.setText("User: " + user);
            score();
        }
    }

    private void score(){
        sharedPreferences = getSharedPreferences(NAME_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editorConfig = sharedPreferences.edit();
        String att = sharedPreferences.getString("ATT", "");
        Integer at = Integer.parseInt(att);
        at = at + 1;
        txtNumberScore.setText("Actual Score: " + at.toString());
        editorConfig.putString("SCORE", at.toString() );
        editorConfig.commit();
        String accu = sharedPreferences.getString("SCORE", "");
        Integer acc = Integer.parseInt(accu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    // Fetch the stored data in onResume()
    // Because this is what will be called
    // when the app opens again
    @Override
    protected void onResume() {
        super.onResume();
        // Fetching the stored data
        // from the SharedPreference
        sharedPreferences = getSharedPreferences(NAME_FILE, MODE_PRIVATE);
        String att = sharedPreferences.getString("ATT", "");
        Integer at = Integer.parseInt(att);
        at = at + 1;
        
        // Setting the fetched data
        // in the EditTexts
        txtNumberScore.setText("Actual Score: " + at.toString());
        String accu = sharedPreferences.getString("SCORE", "");
        Integer acc = Integer.parseInt(accu);
        acumulador = acumulador+acc;
        txtAccuScore.setText("Accumulated Score: " + acumulador.toString());
    }

    // Store the data in the SharedPreference
    // in the onPause() method
    // When the user closes the application
    // onPause() will be called
    // and data will be stored
    @Override
    protected void onPause() {
        super.onPause();
        // Creating a shared pref object
        // with a file name "configuration"
        // in private mode
        sharedPreferences = getSharedPreferences(NAME_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editorConfig = sharedPreferences.edit();

        // write all the data entered by the user in SharedPreference and apply

        String att = sharedPreferences.getString("ATT", "");
        Integer at = Integer.parseInt(att);
        at = at + 1;
        editorConfig.putString("SCORE", at.toString() );
        editorConfig.apply();
    }
}