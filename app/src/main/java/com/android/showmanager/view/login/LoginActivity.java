package com.android.showmanager.view.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.showmanager.R;
import com.android.showmanager.utils.MySharedPrefs;
import com.android.showmanager.view.list.ShowListActivity;

public class LoginActivity extends AppCompatActivity {
    EditText name, password;
    Button submit;
    private boolean firstRun = true;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_actvity);
        sharedPreferences = getSharedPreferences(MySharedPrefs.sharedPref, Context.MODE_PRIVATE);
        firstRun = sharedPreferences.getBoolean("first_run", true);
        init();
    }

    private void init() {
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.nextButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty() && password.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter user name and password!!!", Toast.LENGTH_LONG).show();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("is_logged_out");
                    editor.putBoolean("is_logged_out", false);
                    editor.apply();
                    editor.commit();
                    if (sharedPreferences.getBoolean("first_run", true)) {
                        editor.putBoolean("first_run", false);
                    }
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, ShowListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }
            }
        });
    }
}