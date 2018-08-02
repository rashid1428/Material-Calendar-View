package com.applandeo.materialcalendarsampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by Mateusz Kornakiewicz on 26.05.2017.
 */

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);

        List<EventDay> events = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        EventDay event = new EventDay(calendar, R.drawable.sample_icon_1);
        events.add(event);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DAY_OF_MONTH, 2);
        events.add(new EventDay(calendar1, R.drawable.sample_icon_2));

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DAY_OF_MONTH, 5);
        events.add(new EventDay(calendar2, R.drawable.sample_icon_3));

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);

        Calendar min = Calendar.getInstance();
        min.add(Calendar.MONTH, -2);

        Calendar max = Calendar.getInstance();
        max.add(Calendar.MONTH, 2);

//        calendarView.setMinimumDate(min);
//        calendarView.setMaximumDate(max);

        for(int i = 0; i < 1000; i++){
            Calendar calendar3 = Calendar.getInstance();
            calendar3.add(Calendar.DAY_OF_MONTH, i);
            events.add(new EventDay(calendar3, R.drawable.sample_icon_3));
        }

        calendarView.setEvents(events);

        calendarView.setDisabledDays(getDisabledDays());

        calendarView.setOnDayClickListener(eventDay ->
                Toast.makeText(getApplicationContext(),
                        eventDay.getCalendar().getTime().toString() + " "
                                + eventDay.isEnabled(),
                        Toast.LENGTH_SHORT).show());

        Button setDateButton = (Button) findViewById(R.id.setDateButton);
        setDateButton.setOnClickListener(v -> {
            String currentDate = calendarView.getCurrentPageDate().getTime().toString();
            Toast.makeText(getApplicationContext(), currentDate, Toast.LENGTH_LONG).show();
            System.out.println("CURRENT DATE : " + currentDate);

            try {
                Calendar randomCalendar = getRandomCalendar();
                String text = randomCalendar.getTime().toString();
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                System.out.println("DATE : " + text);
                calendarView.setDate(randomCalendar);
            } catch (OutOfDateRangeException exception) {
                exception.printStackTrace();

                Toast.makeText(getApplicationContext(),
                        "Date is out of range",
                        Toast.LENGTH_LONG).show();
            }
        });
    }



    private List<Calendar> getDaysWithIcons() {
        List<Calendar> calendars = new ArrayList<>();

        for(int i = 0; i < 1000; i++){
            Calendar calendar = DateUtils.getCalendar();
            calendar.add(Calendar.DAY_OF_YEAR, i);
            calendars.add(calendar);
        }

        return calendars;
    }

    private List<Calendar> getDisabledDays() {
        Calendar firstDisabled = DateUtils.getCalendar();
        firstDisabled.add(Calendar.DAY_OF_MONTH, 2);

        Calendar secondDisabled = DateUtils.getCalendar();
        secondDisabled.add(Calendar.DAY_OF_MONTH, 1);

        Calendar thirdDisabled = DateUtils.getCalendar();
        thirdDisabled.add(Calendar.DAY_OF_MONTH, 18);

        List<Calendar> calendars = new ArrayList<>();
        calendars.add(firstDisabled);
        calendars.add(secondDisabled);
        calendars.add(thirdDisabled);
        return calendars;
    }

    private Calendar getRandomCalendar() {
        Random random = new Random();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, random.nextInt(99));

        return calendar;
    }
}
