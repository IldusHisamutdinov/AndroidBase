package com.example.lesson4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private String city;
    private String town;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textTown(); //пропишем город

        List<WeatherData> list = Arrays.asList(
                new WeatherData("02 ", "Mn", "25 С", "18 С"),
                new WeatherData("03 ", "Tu", "23 С", "18 С"),
                new WeatherData("04 ", "Wd", "27 С", "20 С"),
                new WeatherData("05 ", "Th", "20 С", "14 С"),
                new WeatherData("06 ", "Fr", "23 С", "16 С"),
                new WeatherData("07 ", "Sat", "25 С", "18 С")
        );

        SimpleAdapter adapter = new SimpleAdapter();
        RecyclerView rv = findViewById(R.id.rv);
        LinearLayoutManager ltManager = new LinearLayoutManager(getBaseContext());
        rv.setLayoutManager(ltManager);
        rv.setAdapter(adapter);
        adapter.setData(list);
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


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
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Const.SITE));
                startActivity(browserIntent);
            }
        });

    }

    public void textTown() {
        // получаем элемент textView
        TextView textView2 = (TextView) findViewById(R.id.city);
        // получаем ресурс
        String message = getResources().getString(R.string.ufa);
        // переустанавливаем у него текст
        TextView textView = findViewById(R.id.city);
        Intent intent = getIntent();
        town = intent.getStringExtra("town");
        if (town == null) {
            textView2.setText(message);
        } else {
            textView.setText(town);
        }
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