package com.example.ludwig.financialapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button next;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setListeners();
    }

    private void initViews(){
        next = (Button) findViewById(R.id.btnNext);
        editTextFirstName = (EditText) findViewById(R.id.firstName);
        editTextLastName = (EditText) findViewById(R.id.lastName);
    }

    private void setListeners(){
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btnNext:
                saveUserInfo(v);
                logIn();
                break;
            default:
        }
    }

    private void logIn(){
        Intent intentLanding = new Intent(getApplicationContext(), LandingActivity.class);
        startActivity(intentLanding);
    }

    private void saveUserInfo(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("firstName", editTextFirstName.getText().toString());
        editor.putString("lastName", editTextLastName.getText().toString());
        editor.apply();
    }
}
