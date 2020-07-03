package com.example.lesson6fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class Fragment1 extends Fragment implements View.OnClickListener {

    public int counter = 0;
    Activity activity;

    public Fragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_1, container, false);
        Button button = rootView.findViewById(R.id.button1);
        button.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void onClick(View view) {
        counter++;
        Toast.makeText(getActivity(), "Количество нажатий кнопки: " + counter,
                Toast.LENGTH_SHORT).show();

        try {
            ((Postman) activity).fragmentMail(counter);
        } catch (ClassCastException ignored) {

        }
    }

}
