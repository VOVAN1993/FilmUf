package com.example.Start.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.Start.R;
import com.example.Start.util.NetworkUtil;

public class RegistrationActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
    }


    public void onClick(View view){
        switch (view.getId()){
            case R.id.regRegButton:
                EditText login = (EditText) findViewById(R.id.regLogin);
                EditText regPass = (EditText) findViewById(R.id.regPass);
                EditText age = (EditText) findViewById(R.id.regAge);
                EditText sex = (EditText) findViewById(R.id.regSex);
                EditText city = (EditText) findViewById(R.id.regCity);
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);

                NetworkUtil.addUser(regPass.getText().toString(),
                        login.getText().toString(),
                        age.getText().toString(),
                        sex.getText().toString());
                imm.hideSoftInputFromWindow(login.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(regPass.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(age.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(city.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(sex.getWindowToken(), 0);
                MainTabActivity.tabs.setCurrentTab(1);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        MainTabActivity.tabs.setCurrentTab(3);
    }
}
