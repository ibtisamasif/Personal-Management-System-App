package com.example.hp.pms_project.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hp.pms_project.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUserName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfPassword;
    private Button btnRegister;
    private String pass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etEmail = (EditText) findViewById(R.id.etmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfPassword = (EditText) findViewById(R.id.etConfPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String userName = etUserName.getText().toString();
                if (!isValidUserName(userName)) {

                    etUserName.setError("Invalid User Name");
                }
                final String email = etEmail.getText().toString();
                if (!isValidEmail(email)) {

                    etEmail.setError("Invalid Email");
                }

                pass = etPassword.getText().toString();
                if (!isValidPassword(pass)) {

                    etPassword.setError("Password cannot be empty");
                }

                final String confirmPass = etConfPassword.getText().toString();
                if (!isValidConfrimPassword(confirmPass)) {

                    etConfPassword.setError("Password not match");
                }

                if (isValidUserName(userName) && isValidEmail(email) && isValidPassword(pass) && isValidConfrimPassword(confirmPass)) {

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);

                }
            }
        });
    }

    private boolean isValidUserName(String userName) {
        if (userName.length() >= 2 && userName.length() <= 15) {
            return true;
        }
        return false;
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 6) {
            return true;
        }
        return false;
    }

    private boolean isValidConfrimPassword(String confirmPassword) {
        if (confirmPassword.equals(pass)) {
            return true;
        }
        return false;
    }
}
