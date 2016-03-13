package com.roberterrera.foodpoisoning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LocationActivity extends AppCompatActivity {

    String userSymptom;
    String userFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        userSymptom = getIntent().getStringExtra("symptom");
        userFood = getIntent().getStringExtra("food");


        TextView textView= (TextView)findViewById(R.id.textView_location);
        final EditText editText = (EditText)findViewById(R.id.edittext_location);


        findViewById(R.id.button_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = editText.getText().toString();
                Intent intent = new Intent(LocationActivity.this, ResultsActivity.class);
                intent.putExtra("symptom", userSymptom);
                intent.putExtra("food", userFood);
                intent.putExtra("location", userInput);
                startActivity(intent);
            }
        });

    }
}
