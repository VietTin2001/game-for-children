package com.example.final_project.entities;

import java.util.List;

public class Question {

    private int number;
    private int iD;
    private List<Answer> listAnswer;

    public Question(int number, int iD, List<Answer> listAnswer) {
        this.number = number;
        this.iD = iD;
        this.listAnswer = listAnswer;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getID() {
        return iD;
    }

    public void setID(int iD) {
        this.iD = iD;
    }

    public List<Answer> getListAnswer() {
        return listAnswer;
    }

    public void setListAnswer(List<Answer> listAnswer) {
        this.listAnswer = listAnswer;
    }
}
