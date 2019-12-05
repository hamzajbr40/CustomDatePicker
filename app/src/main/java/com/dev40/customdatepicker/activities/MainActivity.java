package com.dev40.customdatepicker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dev40.customdatepicker.R;
import com.dev40.customdatepicker.adapters.DateAdapter;
import com.dev40.customdatepicker.model.DateItem;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ArrayList<DateItem> months, days, years;
    private RecyclerView days_rv, months_rv, years_rv;
    private DateAdapter monthAdapter, dayAdapter, yearAdapter;
    private LinearLayoutManager monthLayout, yearLayout, dayLayout;
    private TextView months_pointer, days_pointer, years_pointer;
    private String day, month = "JAN", year = "1980";//init values
    private int daysOfMonth = 31;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        days_pointer = findViewById(R.id.days_pointer);
        days_rv = findViewById(R.id.days_rv);
        months_pointer = findViewById(R.id.months_pointer);
        months_rv = findViewById(R.id.months_rv);
        years_pointer = findViewById(R.id.years_pointer);
        years_rv = findViewById(R.id.years_rv);

        setMonthsAdapter();
        setYearsAdapter();
        setDaysAdapter();

        //days
        dayLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);

        days_rv.setLayoutManager(dayLayout);

        days_rv.getLayoutManager().scrollToPosition(Integer.MAX_VALUE / 2);

        final SnapHelper daysnapHelper = new LinearSnapHelper();
        daysnapHelper.attachToRecyclerView(days_rv);

        days_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View centerView = daysnapHelper.findSnapView(dayLayout);
                    int pos = dayLayout.getPosition(centerView);
                    days_pointer.setVisibility(View.VISIBLE);
                    day = dayAdapter.getItem(pos).title;
                    dayAdapter.selectItem(pos);

                    Toast.makeText(MainActivity.this, day, Toast.LENGTH_SHORT).show();

                }
            }
        });

        //months
        monthLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        months_rv.setLayoutManager(monthLayout);
        final SnapHelper monthsnapHelper = new LinearSnapHelper();
        monthsnapHelper.attachToRecyclerView(months_rv);
        months_rv.setOnFlingListener(monthsnapHelper);


        months_rv.getLayoutManager().scrollToPosition(Integer.MAX_VALUE / 2);


        months_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View centerView = monthsnapHelper.findSnapView(monthLayout);
                    int pos = monthLayout.getPosition(centerView);
                    months_pointer.setVisibility(View.VISIBLE);
                    month = monthAdapter.getItem(pos).title;
                    monthAdapter.selectItem(pos);

                    Toast.makeText(MainActivity.this, month, Toast.LENGTH_SHORT).show();
                }
            }
        });


        //years
        yearLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        years_rv.setLayoutManager(yearLayout);

        years_rv.getLayoutManager().scrollToPosition(Integer.MAX_VALUE / 2);

        final SnapHelper yearsnapHelper = new LinearSnapHelper();
        yearsnapHelper.attachToRecyclerView(years_rv);

        years_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View centerView = yearsnapHelper.findSnapView(yearLayout);

                    years_pointer.setVisibility(View.VISIBLE);
                    int pos = yearLayout.getPosition(centerView);
                    year = yearAdapter.getItem(pos).title;
                    yearAdapter.selectItem(pos);


                    Toast.makeText(MainActivity.this, year, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    private void setMonthsAdapter() {
        months = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.months)));
        for (String title : titles) {
            DateItem item = new DateItem();
            item.type = DateItem.DATE_ITEM_TYPE.MONTH;
            item.title = title;
            months.add(item);
        }
        monthAdapter = initDateAdapter(months);
        months_rv.setAdapter(monthAdapter);
    }

    private void setYearsAdapter() {

        years = new ArrayList<>();

        for (int i = 1980; i < 2011; i++) {
            DateItem item = new DateItem();
            item.title = String.valueOf(i);
            item.type = DateItem.DATE_ITEM_TYPE.YEAR;
            years.add(item);
        }
        yearAdapter = initDateAdapter(years);
        years_rv.setAdapter(yearAdapter);
    }

    private void setDaysAdapter() {
        days = new ArrayList<>();

        for (int i = 1; i < daysOfMonth + 1; i++) {
            DateItem item = new DateItem();
            item.title = String.valueOf(i);
            item.type = DateItem.DATE_ITEM_TYPE.DAY;
            days.add(item);
        }
        dayAdapter = initDateAdapter(days);
        days_rv.setAdapter(dayAdapter);
    }

    private void validateNumberOfDays() {
        switch (month) {
            case "JAN":
            case "MAR":
            case "MAY":
            case "JUL":
            case "AUG":
            case "OCT":
            case "DEC":
                daysOfMonth = 31;
                break;
            case "FEB":
                if (isLeapYear(Integer.parseInt(year))) {
                    daysOfMonth = 29;
                } else {
                    daysOfMonth = 28;
                }
                break;
            case "APR":
            case "JUN":
            case "SEP":
            case "NOV":
                daysOfMonth = 30;
                break;
        }
        setDaysAdapter();
    }


    private DateAdapter initDateAdapter(ArrayList<DateItem> list) {
        return new DateAdapter(this, list, new DateAdapter.IDate() {
            @Override
            public void onClick(View v) {

            }

            @Override
            public void onFocus(DateItem item) {
                switch (item.type) {
                    case DAY:
                        //no action
                        break;
                    case MONTH:
                        validateNumberOfDays();
                        break;
                    case YEAR:
                        //no action
                        validateNumberOfDays();
                        break;
                    default:
                        //no action
                        break;
                }
            }
        });
    }


    boolean isLeapYear(int year) {
        if (year % 4 != 0) {
            return false;
        } else if (year % 400 == 0) {
            return true;
        } else if (year % 100 == 0) {
            return false;
        } else {
            return true;
        }
    }
}
