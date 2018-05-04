package com.example.rmicaroline.topquiz.Controleur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmicaroline.topquiz.Model.Question;
import com.example.rmicaroline.topquiz.Model.QuestionBank;
import com.example.rmicaroline.topquiz.R;

import java.util.Arrays;

import static java.lang.System.out;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView mQuestionText;
    private Button mAnswer1Button;
    private Button mAnswer2Button;
    private Button mAnswer3Button;
    private Button mAnswer4Button;
    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;
    private int mNumberOfQuestions;
    private int mScore;
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "currentScore";
    public static final String BUNDLE_STATE_QUESTION = "currentQuestion";

    private boolean mEnableTouchEvents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        System.out.println("GameActivity::onCreate()");

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        } else {
            mScore = 0;
            mNumberOfQuestions = 4;
        }

        mQuestionText = (TextView) findViewById(R.id.activity_game_question_text);
        mAnswer1Button = (Button) findViewById(R.id.activity_game_answer1_btn);
        mAnswer2Button = (Button) findViewById(R.id.activity_game_answer2_btn);
        mAnswer3Button = (Button) findViewById(R.id.activity_game_answer3_btn);
        mAnswer4Button = (Button) findViewById(R.id.activity_game_answer4_btn);


        mQuestionBank = this.generateQuestions();

        mEnableTouchEvents = true;

        // Use the same listener for the four buttons.
        // The tag value will be used to distinguish the button triggered
        mAnswer1Button.setOnClickListener(this);
        mAnswer2Button.setOnClickListener(this);
        mAnswer3Button.setOnClickListener(this);
        mAnswer4Button.setOnClickListener(this);

        // Use the tag property to 'name' the buttons
        mAnswer1Button.setTag(0);
        mAnswer2Button.setTag(1);
        mAnswer3Button.setTag(2);
        mAnswer4Button.setTag(3);

        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);
    }


    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mNumberOfQuestions);

        super.onSaveInstanceState(outState);
    }

    public QuestionBank generateQuestions(){
        Question question1 = new Question("Who is the creator of Android?",
                Arrays.asList("Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "Paul Smith"),
                0);

        Question question2 = new Question("When did the first man land on the moon?",
                Arrays.asList("1958",
                        "1962",
                        "1967",
                        "1969"),
                3);

        Question question3 = new Question("What is the house number of The Simpsons?",
                Arrays.asList("42",
                        "101",
                        "666",
                        "742"),
                3);

        Question question4 = new Question ("What is the largest stadium in the world ?",
                Arrays.asList("Campnou stadium in Barcelona",
                        "Stade de France in Paris",
                       "Marcel-Picot Stadim in Nancy",
                        "Azteca Stadium in Mexico city"),
                        3);

        Question question5 = new Question ("Which country is the origin of the cocktail Mojito?",
                Arrays.asList("France",
                        "Cuba",
                        "Indonésia",
                        "Mexico"),
                1);

        Question question6 = new Question ("Who was the first president of the USA?",
                Arrays.asList("James Madison",
                        "Andrew Johnson",
                        "John Adams",
                        "George Washington"),
                3);

        Question question7 = new Question ("What was the name of AC / DC s lead singer who died in 1980?",
                Arrays.asList("Bon Scott",
                        "Bryan Johnson",
                        "Malcolm Young",
                        "Mick Jagger"),
                3);

        Question question8 = new Question ("What is the biggest oil company in the United States?",
                Arrays.asList("BP",
                        "TOTAL",
                        "EXXON MOBIL",
                        "PETROBRAS"),
                2);



        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3,
                question4,
                question5,
                question6,
                question7,
                question8));

    }


    private void displayQuestion(final Question question) {
        // Set the text for the question text view and the four buttons
        mQuestionText.setText(question.getQuestion());
        mAnswer1Button.setText(question.getChoiceList().get(0));
        mAnswer2Button.setText(question.getChoiceList().get(1));
        mAnswer3Button.setText(question.getChoiceList().get(2));
        mAnswer4Button.setText(question.getChoiceList().get(3));
    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        if (responseIndex == mCurrentQuestion.getAnswerIndex()) {
            Toast.makeText(getApplicationContext(), "Good Answer", Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            Toast.makeText(getApplicationContext(), "Wrong Answer", Toast.LENGTH_SHORT).show();
        }

        mEnableTouchEvents = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // If this is the last question, ends the game.
                // Else, display the next question.
                if (--mNumberOfQuestions == 0) {
                    // No question left, end the game
                    endGame();
                    mEnableTouchEvents = true;
                } else {
                    mCurrentQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mCurrentQuestion);
                    mEnableTouchEvents = true;
                }
            }
        }, 2000); // LENGTH_SHORT is usually 2 second long



    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    private void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Well done!")
                .setMessage("Your score is " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();

    }


    @Override
    protected void onStart() {
        super.onStart();
        out.println("GameActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        out.println("GameActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        out.println("GameActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        out.println("GameActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        out.println("GameActivity::onDestroy()");
    }
}
