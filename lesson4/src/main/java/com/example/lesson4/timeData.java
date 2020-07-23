package com.example.lesson4;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class timeData {
// установим дату
    public static String dateNow()                                    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd.MM.yyyy");
        String formate_date = sdf.format(calendar.getTime());
        return formate_date;
    }
}
