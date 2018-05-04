package com.example.rmicaroline.topquiz.Controleur;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmicaroline.topquiz.Model.User;
import com.example.rmicaroline.topquiz.R;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class MainActivity extends AppCompatActivity {


    // declaration des éléments graphiques
    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private Button mHistoryButton;
    private User mUser;
    private static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    private SharedPreferences mPreferences;
    public static final String KEY_SCORE = "KEY_SCORE";
    public static final String KEY_FIRSTNAME = "KEY_FIRSTNAME";
    public static final String KEY_SCORE_HIST = "KEY_SCORE_HIST";
    public static final String KEY_FIRSTNAME_HIST = "KEY_FIRSTNAME_HIST";
    public ArrayList<Integer> mListScore;
    public ArrayList<String> mListFirstname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = getPreferences(MODE_PRIVATE);


        // branchement à des variables
        mGreetingText = (TextView) findViewById(R.id.activity_main_greeting_txt);
        mNameInput = (EditText) findViewById(R.id.activity_main_name_input);
        mPlayButton = (Button) findViewById(R.id.activity_main_play_btn);
        mHistoryButton = (Button) findViewById(R.id.activity_main_hist_btn);


        mPlayButton.setEnabled(false);

        mUser = new User();

        greetUser();




        mNameInput.addTextChangedListener(new TextWatcher() {
                                              @Override
                                              public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                              }

                                              @Override
                                              public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                  mPlayButton.setEnabled(s.toString().length() != 0);
                                              }

                                              @Override
                                              public void afterTextChanged(Editable s) {

                                              }
                                          });

                mPlayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // The user just clicked

                        mUser.setFirstName(mNameInput.getText().toString());

                        //Ecriture prenom dans préférences
                        mPreferences.edit().putString(KEY_FIRSTNAME,mUser.getFirstName()).apply();

                        // def de l'intent et ouverture nouvelle activité GAME ACTIVITY
                        Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                        startActivityForResult(gameActivity, GAME_ACTIVITY_REQUEST_CODE);
                    }
                });

                mHistoryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent historyActivity = new Intent (MainActivity.this, HistoryActivity.class);
                        if (mListFirstname.size()>=1 && mListScore.size()>=1) {
                            historyActivity.putExtra(KEY_SCORE_HIST, mListScore);
                            historyActivity.putExtra(KEY_FIRSTNAME_HIST, mListFirstname);
                            startActivity(historyActivity);
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            // Fetch the score from the Intent
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);

            //Ecriture score dans préférences
            mPreferences.edit().putInt(KEY_SCORE,score).apply();

            greetUser();
        }
    }




    protected void greetUser (){

        //Lecture prénom dans préférences
        String firstname = getPreferences(MODE_PRIVATE).getString(KEY_FIRSTNAME,null);

        if (null != firstname){
            // Lecture score dans préférences
            int score = getPreferences(MODE_PRIVATE).getInt(KEY_SCORE,0);

            // Mise à jour du texte d'acceuil si utilisateur est déjà connu
            String fullText = "Welcome back " + firstname
                            + "\nYour last score was " + score
                            + "\nWill you do better this time? ";
            mGreetingText.setText(fullText);

            //Mise à jour du prénom dans le champ édit
            mNameInput.setText(firstname);
            mNameInput.setSelection(firstname.length());
            mPlayButton.setEnabled(true);


            mListScore.add(score);
            mListFirstname.add(firstname);

        }


    }


    @Override
    protected void onStart() {
        super.onStart();
        out.println("MainActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        out.println("MainActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        out.println("MainActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        out.println("MainActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        out.println("MainActivity::onDestroy()");
    }
}
