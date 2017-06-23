package com.massandy.mydaterangepicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.darwindeveloper.onecalendar.clases.Day;
import com.darwindeveloper.onecalendar.views.OneCalendarView;

/**
 * Created by massandy on 23/06/17.
 * from lib : https://android-arsenal.com/details/1/5414
 */

public class OneCalendarFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_calendar);

        final OneCalendarView calendarView = (OneCalendarView) findViewById(R.id.oneCalendar);

        //el siguiente fragmento puede ser usado para capturar los swipes en el calendar
        calendarView.setOnCalendarChangeListener(new OneCalendarView.OnCalendarChangeListener() {

            /**
             * notifica al usuario que el calendario a cambiado al mes anterior
             */
            @Override
            public void prevMonth() {
                //hacer algo aqui
                Log.d("caltest", "prevmonth changed");
            }

            /**
             * notifica al usuario que el calendario a cambiado al mes siguiente
             */
            @Override
            public void nextMonth() {
                //hacer algo aqui
                Log.d("caltest", "nextmonth changed");
            }
        });


        //el siguiente fragmento de codigo muestra como obtener los datos de un dia en el calendario
        //ademas de realizar otras acciones
        calendarView.setOneCalendarClickListener(new OneCalendarView.OneCalendarClickListener() {

            /**
             * cuando se da click en un dia en el calendario mostrado
             *
             * @param day      un Objeto de tipo Day del cual podemos llara a su metodo getDate() para recuperar una fecha
             * @param position posicion desde 0-41, que ocupa en el calendario actual
             */
            @Override
            public void dateOnClick(Day day, int position) {
                //recuerde que en java los meses inician desde 0
                Log.d("caltest", "click : " + position + " " + day.toString());
            }

            /**
             * cuando se da click prolongado en un dia en el calendario mostrado
             *
             * @param day      un Objeto de tipo Day del cual podemos llara a su metodo getDate() para recuperar una fecha
             * @param position posicion desde 0-41, que ocupa en el calendario actual
             */
            @Override
            public void dateOnLongClick(Day day, int position) {
                Log.d("caltest", "long click : " + position + " " + day.toString());
            }
        });


    }
}
