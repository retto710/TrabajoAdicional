package com.example.anthony.trabajoadicional.Entities;


import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;

public class Question extends SugarRecord<Question>{
    private String category;
    private String difficulty;
    private String question;
    private String answer;

    public Question(String category, String difficulty, String question, String answer){
        this.setCategory(category);
        this.setDifficulty(difficulty);
        this.setQuestion(question);
        this.setAnswer(answer);
    };

    public String getCategory() {
        return category;
    }


    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public String toJson(){
        String jsonText = null;

        JSONObject js = new JSONObject();

        try{
            js.put("id",getId());
            js.put("category",getCategory());
            js.put("difficulty",getDifficulty());
            js.put("question",getQuestion());
            js.put("correct_answer",getAnswer());

            jsonText = js.toString();
        }catch (JSONException e){

            e.printStackTrace();
        }
        return jsonText;
    }
}
