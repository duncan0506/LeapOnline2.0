package com.peoplecoachingworks.leapstudio20;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class KeyLessonPage extends AppCompatActivity {

    FloatingActionButton fabAddKeyLesson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_lesson_page);

        fabAddKeyLesson = findViewById(R.id.fabAddKeyLesson);

        fabAddKeyLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AddKeyLessonActivity.class);
                startActivity(i);
            }
        });


    }
}
