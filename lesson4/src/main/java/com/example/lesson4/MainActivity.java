package com.example.lesson4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private String city = "Ufa";
    private static final String CITY = "city";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String text = getIntent().getStringExtra("TEXT");
        TextView textView = findViewById(R.id.city);
        textView.setText(text);

        Button startAct = findViewById(R.id.button);
        startAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CitySelection.class);
                startActivity(intent);
            }
        });

        Button go = findViewById(R.id.button3);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gismeteo.ru/weather-ufa-4588/"));
                startActivity(browserIntent);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("CITY", city);
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);
        city = saveInstanceState.getString("CITY");
    }
}
