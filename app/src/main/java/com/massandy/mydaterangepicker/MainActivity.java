package com.massandy.mydaterangepicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements DateRangePickerFragment.OnDateRangeSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DateRangePickerFragment dateRangePickerFragment= DateRangePickerFragment.newInstance(MainActivity.this,false);
//        dateRangePickerFragment.show(getSupportFragmentManager(),"datePicker");

        Button button1 = (Button) findViewById(R.id.my_button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDateRangePicker1();
            }
        });

        Button button2 = (Button) findViewById(R.id.my_button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDateRangePicker2();
            }
        });

        Button button3 = (Button) findViewById(R.id.my_button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDateRangePicker3();
            }
        });

        Button button4 = (Button) findViewById(R.id.my_button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDateRangePicker4();
            }
        });

        Button button5 = (Button) findViewById(R.id.my_button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDateRangePicker5();
            }
        });

        Button button6 = (Button) findViewById(R.id.my_button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDateRangePicker6();
            }
        });
    }

    private void goToDateRangePicker1() {
        Intent intent = new Intent(this, CaldroidRangePickerFragment.class);
        startActivity(intent);
    }

    private void goToDateRangePicker2() {
        Intent intent = new Intent(this, CaldroidRangePickerFragment.class);
        intent.putExtra("minDate", "30-05-2017");
        startActivity(intent);
    }

    private void goToDateRangePicker3() {
        Intent intent = new Intent(this, CaldroidRangePickerFragment.class);
        intent.putExtra("minDate", "30-05-2017");
        intent.putExtra("startDate", "05-06-2017");
        intent.putExtra("endDate", "10-06-2017");
        startActivity(intent);
    }

    private void goToDateRangePicker4() {
        Intent intent = new Intent(this, Sampler.class);
        startActivity(intent);
    }

    private void goToDateRangePicker5() {
        Intent intent = new Intent(this, TimeSquareFragment.class);
        startActivity(intent);
    }

    private void goToDateRangePicker6() {
        Intent intent = new Intent(this, OneCalendarFragment.class);
        startActivity(intent);
    }

    @Override
    public void onDateRangeSelected(int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear) {
        Log.d("range : ","from: "+startDay+"-"+startMonth+"-"+startYear+" to : "+endDay+"-"+endMonth+"-"+endYear );
    }
}
