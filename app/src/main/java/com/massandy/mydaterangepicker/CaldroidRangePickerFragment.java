package com.massandy.mydaterangepicker;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// lib : https://github.com/roomorama/Caldroid
public class CaldroidRangePickerFragment extends AppCompatActivity {
    private final String CALDROID_SELECTABLE_START_DATE= "CALDROID_SELECTABLE_START_DATE";
    private final String CALDROID_SELECTABLE_END_DATE= "CALDROID_SELECTABLE_END_DATE";

    private final String tabSpecStartDatePicker = "start";
    private final String tabSpecEndDatePicker = "end";

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private TabHost tabHost;

    class CalDroidSelectableDate {
        private CaldroidFragment caldroidFragment;
        private Date selectedStartDate;
        private Date selectedEndDate;

        private String tag;

        public CalDroidSelectableDate(String tag) {
            this.tag = tag;
            caldroidFragment = new CaldroidFragment();
        }

        public void setUpArguments() {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, false);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
            caldroidFragment.setArguments(args);
        }

        private void clearDate(Date d) {
            caldroidFragment.clearBackgroundDrawableForDate(d);
            caldroidFragment.clearTextColorForDate(d);
        }


        public void selectStartDate(Date date) {
            if (selectedStartDate != null && !selectedStartDate.equals(date)) {
                clearDate(selectedStartDate);
            }

            ColorDrawable dateSelectedBgColor = new ColorDrawable(getResources().getColor(R.color.calSelectedDateBgColor));
            caldroidFragment.setBackgroundDrawableForDate(dateSelectedBgColor, date);
            caldroidFragment.setTextColorForDate(R.color.calSelectedDateTextColor, date);
            selectedStartDate = date;
        }

        public void selectEndDate(Date date) {
            if (selectedEndDate != null && !selectedEndDate.equals(date) && !selectedEndDate.equals(selectedStartDate)) {
                clearDate(selectedEndDate);
            }
            ColorDrawable dateSelectedBgColor = new ColorDrawable(getResources().getColor(R.color.calSelectedDateBgColor));
            caldroidFragment.setBackgroundDrawableForDate(dateSelectedBgColor, date);
            caldroidFragment.setTextColorForDate(R.color.calSelectedDateTextColor, date);

            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            // set to normal color if date < selectedEndDate
            if (selectedEndDate != null && date.before(selectedEndDate)) {
                start.setTime(date);
                start.add(Calendar.DATE, 1);
                end.setTime(selectedEndDate);

                for (Date dateIter = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), dateIter = start.getTime()) {
                    clearDate(dateIter);
                }
            }

            // set date in range selectedStartDate - selectedEndDate
            if (selectedEndDate == null || date.after(selectedEndDate)) {
                start = Calendar.getInstance();
                end = Calendar.getInstance();
                if (selectedEndDate == null) {
                    start.setTime(selectedStartDate);
                    start.add(Calendar.DATE, 1);
                } else if (date.after(selectedEndDate)) {
                    start.setTime(selectedEndDate);
                    if (selectedStartDate.equals(selectedEndDate))
                        start.add(Calendar.DATE, 1);
                }

                end.setTime(date);

                ColorDrawable dateInRangeBgColor = new ColorDrawable(getResources().getColor(R.color.calDateInRangeBgColor));
                for (Date dateIter = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), dateIter = start.getTime()) {
                    caldroidFragment.setBackgroundDrawableForDate(dateInRangeBgColor, dateIter);
                    caldroidFragment.setTextColorForDate(R.color.calDateInRangeTextColor, dateIter);
                }
            }
            selectedEndDate = date;
        }

        public void redrawSelection() {
            ColorDrawable dateSelectedBgColor = new ColorDrawable(getResources().getColor(R.color.calSelectedDateBgColor));

            if (selectedStartDate != null) {
                caldroidFragment.setBackgroundDrawableForDate(dateSelectedBgColor, selectedStartDate);
                caldroidFragment.setTextColorForDate(R.color.calSelectedDateTextColor, selectedStartDate);
            }

            if (selectedEndDate != null) {
                caldroidFragment.setBackgroundDrawableForDate(dateSelectedBgColor, selectedEndDate);
                caldroidFragment.setTextColorForDate(R.color.calSelectedDateTextColor, selectedEndDate);
            }

            if (selectedStartDate == null || selectedEndDate == null) return;


            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            start.setTime(selectedStartDate);
            start.add(Calendar.DATE, 1);
            end.setTime(selectedEndDate);

            ColorDrawable dateInRangeBgColor = new ColorDrawable(getResources().getColor(R.color.calDateInRangeBgColor));
            for (Date dateIter = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), dateIter = start.getTime()) {
                caldroidFragment.setBackgroundDrawableForDate(dateInRangeBgColor, dateIter);
                caldroidFragment.setTextColorForDate(R.color.calDateInRangeTextColor, dateIter);
            }
        }

        public void clearSelection() {
            if (selectedStartDate != null) {
                clearDate(selectedStartDate);
            }

            if (selectedEndDate != null) {
                clearDate(selectedEndDate);

            }
            if (selectedStartDate == null || selectedEndDate == null) return;

            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            start.setTime(selectedStartDate);
            end.setTime(selectedEndDate);

            for (Date dateIter = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), dateIter = start.getTime()) {
                caldroidFragment.clearBackgroundDrawableForDate(dateIter);
            }
            selectedStartDate = null;
            selectedEndDate = null;
        }

        public void restoreInstance(Bundle savedInstanceState) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState, tag+"_FRAGMENT");
            selectedStartDate = parseDateFromString(savedInstanceState.getString(tag+"_START"));
            selectedEndDate = parseDateFromString(savedInstanceState.getString(tag+"_END"));
        }

        public void saveInstance(Bundle outState){
            if (caldroidFragment != null) {
                caldroidFragment.saveStatesToKey(outState, tag+"_FRAGMENT");
            }
            if (selectedStartDate != null) {
                outState.putString(tag+"_START", dateFormat.format(selectedStartDate));
            }
            if (selectedEndDate != null) {
                outState.putString(tag+"_END", dateFormat.format(selectedEndDate));
            }
        }

    }

    private CalDroidSelectableDate startCalDate;
    private CalDroidSelectableDate endCalDate;

    // Setup listener
    final CaldroidListener listener1 = new CaldroidListener() {

        @Override
        public void onSelectDate(Date date, View view) {
            startCalDate.clearSelection();
            startCalDate.selectStartDate(date);
            enableEndDateTabSelection();
            endCalDate.caldroidFragment.setMinDate(date);

            startCalDate.caldroidFragment.refreshView();
        }

        @Override
        public void onChangeMonth(int month, int year) {
        }

        @Override
        public void onLongClickDate(Date date, View view) {
        }

        @Override
        public void onCaldroidViewCreated() {
//            if (caldroidFragment1.getLeftArrowButton() != null) {
//            }
        }

    };

    final CaldroidListener listener2 = new CaldroidListener() {

        @Override
        public void onSelectDate(Date date, View view) {
            endCalDate.selectEndDate(date);

            endCalDate.caldroidFragment.refreshView();
        }

        @Override
        public void onChangeMonth(int month, int year) {
        }

        @Override
        public void onLongClickDate(Date date, View view) {
        }

        @Override
        public void onCaldroidViewCreated() {
//            if (caldroidFragment1.getLeftArrowButton() != null) {
//            }
        }

    };

    final TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
        @Override
        public void onTabChanged(String tabId) {
            if (tabSpecStartDatePicker.equals(tabId)) {
                startCalDate.clearSelection();
                startCalDate.selectedStartDate = endCalDate.selectedStartDate;
                startCalDate.selectedEndDate = endCalDate.selectedEndDate;

                startCalDate.redrawSelection();
                startCalDate.caldroidFragment.refreshView();

            } else if (tabSpecEndDatePicker.equals(tabId)) {
                endCalDate.clearSelection();
                endCalDate.selectedStartDate = startCalDate.selectedStartDate;
                endCalDate.selectedEndDate = startCalDate.selectedEndDate;

                endCalDate.redrawSelection();
                endCalDate.caldroidFragment.refreshView();
            }
        }
    };

    private Date parseDateFromString(String dateStr) {
        Date res = null;
        try {
            res = dateFormat.parse(dateStr);
        } catch (java.text.ParseException ex) {}
        return res;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caldroid_range_picker);

        startCalDate = new CalDroidSelectableDate(CALDROID_SELECTABLE_START_DATE);
        endCalDate = new CalDroidSelectableDate(CALDROID_SELECTABLE_END_DATE);

        // If Activity is created after rotation
        if (savedInstanceState != null) {
            startCalDate.restoreInstance(savedInstanceState);
            endCalDate.restoreInstance(savedInstanceState);
        }
        // If activity is created from fresh
        else {
            startCalDate.setUpArguments();
            endCalDate.setUpArguments();

            if (getIntent().hasExtra("minDate")) {
                String dateStr = getIntent().getStringExtra("minDate");
                Date d = parseDateFromString(dateStr);
                if (d != null)
                    startCalDate.caldroidFragment.setMinDate(d);
            }

            if (getIntent().hasExtra("startDate")) {
                String dateStr = getIntent().getStringExtra("startDate");
                Date d = parseDateFromString(dateStr);
                if (d != null) {
                    startCalDate.selectedStartDate = d;
                    endCalDate.selectedStartDate = d;
                }
            }

            if (getIntent().hasExtra("endDate")) {
                String dateStr = getIntent().getStringExtra("endDate");
                Date d = parseDateFromString(dateStr);
                if (d != null && startCalDate.selectedStartDate != null) {
                    startCalDate.selectedEndDate = d;
                    endCalDate.selectedEndDate= d;
                }
            }
        }

        // Attach to the activity
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, startCalDate.caldroidFragment);
        t.replace(R.id.calendar2, endCalDate.caldroidFragment);
        t.commit();

        // Setup Caldroid
        startCalDate.caldroidFragment.setCaldroidListener(listener1);

        // Setup Caldroid
        endCalDate.caldroidFragment.setCaldroidListener(listener2);

        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec startDatePage = tabHost.newTabSpec(tabSpecStartDatePicker);
        startDatePage.setContent(R.id.start_date_group);
        startDatePage.setIndicator(getString(R.string.title_tab_start_date));

        TabHost.TabSpec endDatePage = tabHost.newTabSpec(tabSpecEndDatePicker);
        endDatePage.setContent(R.id.end_date_group);
        endDatePage.setIndicator(getString(R.string.ttile_tab_end_date));

        tabHost.addTab(startDatePage);
        tabHost.addTab(endDatePage);
        if (startCalDate.selectedStartDate == null)
            disableEndDateTabSelection();
        tabHost.setOnTabChangedListener(tabChangeListener);

        startCalDate.redrawSelection();
        startCalDate.caldroidFragment.refreshView();
        endCalDate.redrawSelection();
        endCalDate.caldroidFragment.refreshView();
    }

    private void disableEndDateTabSelection() {
        tabHost.getTabWidget().getChildTabViewAt(1).setEnabled(false); // disable end date selection
    }

    private void enableEndDateTabSelection() {
        tabHost.getTabWidget().getChildTabViewAt(1).setEnabled(true); // disable end date selection
    }

    /**
     * Save current states of the Caldroid here
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        startCalDate.saveInstance(outState);
        endCalDate.saveInstance(outState);
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

    }
}
