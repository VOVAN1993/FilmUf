package com.example.Start.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.Start.R;
import com.example.Start.util.BasicUtil;
import com.example.Start.util.NetworkUtil;

public class LoginActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.inInButton:
                EditText loginView = (EditText) findViewById(R.id.inLogin);
                EditText passView = (EditText) findViewById(R.id.inPass);
                String login = loginView.getText().toString();
                String pass = passView.getText().toString();
                loginView.setText("");
                passView.setText("");
                if(login.isEmpty() || pass.isEmpty()) return;
                boolean b = NetworkUtil.containsUser(login);
                if(b) {
                    MainTabActivity.tabs.setCurrentTab(1);
                }
                break;
            case R.id.inRegButton:
                break;
        }
    }
}
