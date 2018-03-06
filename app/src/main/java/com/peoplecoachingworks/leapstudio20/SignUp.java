package com.peoplecoachingworks.leapstudio20;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button btnSignUp = findViewById(R.id.btnSignUp);
        TextView btnSignInHere = findViewById(R.id.btnSignInHere);

        // TODO: Validate info, remember info, sign up and login
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), NavDrawerActivity.class);
                startActivity(i);
            }
        });

        btnSignInHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Login.class);
                startActivity(i);
            }

        });


    }
}
