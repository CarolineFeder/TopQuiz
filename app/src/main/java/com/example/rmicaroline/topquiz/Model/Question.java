package com.example.rmicaroline.topquiz.Model;

import java.util.List;

/**
 * Created by Rémi&Caroline on 26/04/2018.
 */

public class Question {

    private String mQuestion;
    private List<String> mChoiceList;
    private int mAnswerIndex;

    public Question(String question, List<String> choiceList, int answerIndex) {
        mQuestion = question;
        //création de la liste si il y a au moins 1 réponse possible
        if (choiceList.size()>=0) {
            mChoiceList = choiceList;
        }
        // attribution de l'index de la bonne reponse
        // si entier compris entre 0 et nombre de réponse possible
        if (answerIndex >= 0 && answerIndex <= choiceList.size()) {
            mAnswerIndex = answerIndex;
        }
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setChoiceList(List<String> choiceList) {
        //création de la liste si il y a au moins 1 réponse possible
        if (choiceList.size()>=0) {
            mChoiceList = choiceList;
        }
    }

    public List<String> getChoiceList() {
        return mChoiceList;
    }

    public void setAnswerIndex(int answerIndex,List<String> choiceList) {
        // attribution de l'index de la bonne reponse
        // si entier compris entre 0 et nombre de réponse possible
        if (answerIndex >= 0 && answerIndex <= choiceList.size()){
            mAnswerIndex = answerIndex;
        }

    }

    public int getAnswerIndex() {
        return mAnswerIndex;
    }
}
