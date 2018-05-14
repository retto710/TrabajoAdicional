package com.example.anthony.trabajoadicional;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Date;
import com.example.anthony.trabajoadicional.Entities.Score;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private String finalUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnGo = findViewById(R.id.btnGo);
        final EditText txtUsername = findViewById(R.id.edtUsername);
        final EditText editText = findViewById(R.id.edtUsername);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtUsername.getText().toString().isEmpty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setMessage("Type a username").setTitle("Error");
                    AlertDialog dialog = builder.create();

                }
                else   {
                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                    String username= txtUsername.getText().toString();
                    finalUserName=username;
                    Score score = new Score();
                    score.setName(username);
                    Date currentadate = Calendar.getInstance().getTime();
                    score.setDate(currentadate);
                    score.save();
                    Long userid = score.getId();
                    Log.d("useridMain",String.valueOf(userid));
                    intent.putExtra("userName",editText.getText().toString());
                    intent.putExtra("userid",userid);
                    startActivity(intent);
                }

            }
        });
    }
}
