package com.example.lesson4;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;


public class CitySelection extends AppCompatActivity {

    private TextInputEditText city;
    private Pattern checkCity = Pattern.compile("^[А-Я][а-я]{2,}$"); // русский
    private TextInputLayout cityLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);

        city = findViewById(R.id.inputCity);
        cityLayout = findViewById(R.id.city);

        city.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) return;
                TextView tv = (TextView) v;
                String value = tv.getText().toString();
                if (checkCity.matcher(value).matches()) {
                    cityLayout.setError(null);
                } else {
                    cityLayout.setError("Это не город");
                }
            }
        });


        @SuppressLint("CutPasteId") final EditText text = findViewById(R.id.inputCity);
        Button startAct = findViewById(R.id.button2);
        startAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("town", text.getText().toString());
                startActivity(intent);
                finish();
            }
        });


        final ListView list = (ListView) findViewById(R.id.listView);
        @SuppressLint("CutPasteId") final TextView txt = findViewById(R.id.inputCity);
        final String[] city = getResources().getStringArray(R.array.city);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, city);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txt.setText(city[position]);

            }
        });


    }

    public void onClick(View view) {
        Snackbar.make(view, "Выберите город из списка или напишите", Snackbar.LENGTH_LONG)
                .show();
    }

}