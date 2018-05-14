package com.example.anthony.trabajoadicional.BusinessLogic;

import android.util.JsonReader;
import android.util.Log;

import com.example.anthony.trabajoadicional.Entities.Question;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class QuestionService implements IQuestionService {
    @Override
    public ArrayList<Question> getQuestions() {
        ArrayList<Question> categories =null;
        try {
            URL apiUrl =
                    new URL("https://opentdb.com/api.php?amount=50&type=boolean");

            // Create connection
            HttpURLConnection myConnection =
                    (HttpURLConnection) apiUrl.openConnection();

            //Process response
            if (myConnection.getResponseCode() == 200) {
                // Success
                // Further processing here

                //Read response
                InputStream responseBody = myConnection.getInputStream();

                //Use reader for response
                InputStreamReader responseBodyReader =
                        new InputStreamReader(responseBody, "UTF-8");

                //Read json
                JsonReader jsonReader = new JsonReader(responseBodyReader);
                jsonReader.beginObject();
                String responsecode = jsonReader.nextName();
                Log.d("response",responsecode);
                int code =jsonReader.nextInt();
                String codes = String.valueOf(code);
                Log.d("code", codes);
                String result = jsonReader.nextName();
                //Start reading array
                jsonReader.beginArray();
                //Read elements
                categories=new ArrayList<>();
                while(jsonReader.hasNext()){
                    //Read every object
                    jsonReader.beginObject();
                    int id = 0;
                    String category="";
                    String difficulty="";
                    String answer="";
                    String question="";
                    while(jsonReader.hasNext()){
                        String property = jsonReader.nextName();
                        switch (property.toLowerCase()){
                            case "category":
                                category = jsonReader.nextString();
                                break;
                            case "difficulty":
                                difficulty = jsonReader.nextString();
                                break;
                            case "question":
                                question = jsonReader.nextString();
                                break;
                            case "correct_answer":
                                answer = jsonReader.nextString();
                                break;
                            default:
                                jsonReader.skipValue();
                                break;
                        }
                    }
                    //Add item to the list

                    Question objQuestion =
                            new Question(category,difficulty,question,answer);
                    categories.add(objQuestion);
                    jsonReader.endObject();
                }
                jsonReader.endArray();
                jsonReader.endObject();
                jsonReader.close();
                myConnection.disconnect();




            } else {
                // Error handling code goes here

            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
