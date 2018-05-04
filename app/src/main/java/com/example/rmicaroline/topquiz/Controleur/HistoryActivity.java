package com.example.rmicaroline.topquiz.Controleur;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.rmicaroline.topquiz.R;

import static com.example.rmicaroline.topquiz.Controleur.MainActivity.KEY_FIRSTNAME_HIST;
import static com.example.rmicaroline.topquiz.Controleur.MainActivity.KEY_SCORE_HIST;

public class HistoryActivity extends AppCompatActivity {


    private TextView mGreetingText;
    private TextView mPlayer1Text;
    private TextView mPlayer2Text;
    private TextView mPlayer3Text;
    private TextView mPlayer4Text;
    private Button mPerScoreButton;
    private Button mPerNameButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        mGreetingText = (TextView) findViewById(R.id.activity_main_greeting_txt);
        mPlayer1Text = (TextView) findViewById(R.id.activity_history_player1_txt);
        mPlayer2Text = (TextView) findViewById(R.id.activity_history_player2_txt);
        mPlayer3Text = (TextView) findViewById(R.id.activity_history_player3_txt);
        mPlayer4Text = (TextView) findViewById(R.id.activity_history_player4_txt);
        mPerScoreButton = (Button) findViewById(R.id.activity_history_perScore_btn);
        mPerNameButton = (Button) findViewById(R.id.activity_history_perName_btn);

        int[] mScore = getIntent().getIntArrayExtra(KEY_SCORE_HIST);
        String[] mFirstname = getIntent().getStringArrayExtra(KEY_FIRSTNAME_HIST);

        String fullText = mFirstname[0]
                + " with a score of  " + mScore[0];
        mPlayer1Text.setText(fullText);


    }
}
