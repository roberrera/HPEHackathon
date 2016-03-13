package com.roberterrera.foodpoisoning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FoodTypeActivity extends AppCompatActivity {

    public String symptom;
    String food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_type);

        TextView textView= (TextView)findViewById(R.id.textView_food);

        symptom = getIntent().getStringExtra("symptom");


        findViewById(R.id.button_greens).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food = "leafy+greens";
                Intent intent = new Intent(FoodTypeActivity.this, LocationActivity.class);
                intent.putExtra("symptom", symptom);
                intent.putExtra("food", food);
                startActivity(intent);
            }
        });

    }
}
