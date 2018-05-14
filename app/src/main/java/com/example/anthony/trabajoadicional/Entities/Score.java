package com.example.anthony.trabajoadicional.Entities;

import com.orm.SugarRecord;

import java.util.Date;

public class Score extends SugarRecord<Score>{
    private String name;
    private int score;
    private String time;
    private Date date;
    public Score(){
        setScore(0);
    };
    public void correcto(String difficulty){
        if(difficulty.equals("easy"))
            setScore(getScore()+20);
        if(difficulty.equals("medium"))
            setScore(getScore()+40);
        if(difficulty.equals("hard"))
            setScore(getScore()+60);
    }
    public void incorrecto(String difficulty){
        if(difficulty.equals("easy"))
            setScore(getScore()-40);
        if(difficulty.equals("medium"))
            setScore(getScore()-30);
        if(difficulty.equals("hard"))
            setScore(getScore()-20);
    }
    public void finish(int time){
        setScore(getScore()+time);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
