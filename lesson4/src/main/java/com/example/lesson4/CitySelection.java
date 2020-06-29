package com.example.lesson4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;



public class CitySelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);


        CitiesFragment details = new CitiesFragment();
        details.setArguments(getIntent().getExtras());
        // Добавим фрагмент на activity
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_cities, details).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();


    }

}