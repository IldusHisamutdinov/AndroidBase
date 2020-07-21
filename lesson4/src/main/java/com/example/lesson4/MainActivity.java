package com.example.lesson4;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private ProgressDialog Indicator;
    private String city;
    private String temp;
    private Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textTown(); //пропишем город

    }

//   название города для url в initHttp()
    public String changeCity(String city) {

        TextView textView = findViewById(R.id.inputCity);
        Intent intent = getIntent();
        String town = intent.getStringExtra("town");
        if (town == null) {
            town = "Ufa";
        } else {
            String finalTown = town;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText(finalTown); // вставил в поток,иначе выходит сообщение
                }
            });
        }

        return town;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void textTown() {

//      получаем элемент textView
        TextView nameCity = (TextView) findViewById(R.id.inputCity);
        // получаем ресурс
        String message = getResources().getString(R.string.ufa);
//      переустанавливаем у него текст
        TextView textView = findViewById(R.id.inputCity);
        Intent intent = getIntent();
        String town = intent.getStringExtra("town");
        if (town == null) {
            nameCity.setText(message);
        }

        new Thread(() -> {
            initHttp();

        }).start();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, CitySelection.class);
                startActivity(intent);
                break;
            case R.id.site:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Const.SITE));
                startActivity(browserIntent);
                break;
        }
        return true;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initHttp() {
        HttpsURLConnection urlConnection = null;
        String town = changeCity(city);

        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + town + "&units=metric&appid=" + BuildConfig.KEY + "&lang=ru");
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);

//          если неверный город, то пишем н/а

            if (urlConnection.getResponseCode() != 200) {
                handler.post(() -> {
                    TextView city = findViewById(R.id.inputCity);
                    city.setText("Н/А");
                    TextView clear = findViewById(R.id.description);
                    clear.setText("Н/А");
                    TextView temp = findViewById(R.id.temp);
                    temp.setText("Н/А");
                    TextView speed = findViewById(R.id.speed);
                    speed.setText("Н/А");
                });
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String result = in.lines().collect(Collectors.joining());
            Gson gson = new Gson();
            ResponseWeather resultWeather = gson.fromJson(result, ResponseWeather.class);

            handler.post(() -> {
                TextView description = findViewById(R.id.description);
                description.setText(" " + resultWeather.getWeather().get(0).getDescription());
                TextView clouds = findViewById(R.id.clouds);
                clouds.setText("Облачность " + resultWeather.getClouds().getAll() + " %");
                TextView temp = findViewById(R.id.temp);
                temp.setText("" + resultWeather.getMain().getTemp() + " ℃");
                TextView speed = findViewById(R.id.speed);
                speed.setText("" + resultWeather.getWind().getSpeed());
                TextView humidity = findViewById(R.id.humidity);
                humidity.setText("Влажность " + resultWeather.getMain().getHumidity() + " %");
                TextView pressure = findViewById(R.id.press);
                pressure.setText("Давление " + resultWeather.getMain().getPressure() + " гПа");
                TextView date = findViewById(R.id.date);
                date.setText((timeData.dateNow()));

            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    //   Обрабатываем нажатие кнопки Refresh:
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void startProgress(View v) {

//      Создаем ProgressDialog
        Indicator = new ProgressDialog(this);
//      Настраиваем для ProgressDialog название его окна:
        Indicator.setMessage(getResources().getString(R.string.task));
//      Настраиваем стиль отображаемого окна:
        Indicator.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//      Отображаем ProgressDialog:
        Indicator.show();

//      Создаем параллельный поток sleep

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

//              Устанавливаем время задержки
                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

//              закрываем ProgressDialog:
                Indicator.cancel();

            }
        }).start();

//      выводим погоду
        new Thread(() -> {
            initHttp();

        }).start();
    }

    //     сохранение города и температуры
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("CITY", city);
        outState.putString("TEMP", String.valueOf(temp));
    }


    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);
        city = saveInstanceState.getString("CITY");
        temp = saveInstanceState.getString("TEMP");
    }

}