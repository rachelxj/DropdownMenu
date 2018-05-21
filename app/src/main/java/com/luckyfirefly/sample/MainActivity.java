package com.luckyfirefly.sample;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.luckyfirefly.dropdownmenu.DropDownMenu;
import com.luckyfirefly.dropdownmenu.MaskView;
import com.luckyfirefly.dropdownmenu.adapter.BaseDropdownView;
import com.luckyfirefly.dropdownmenu.adapter.DropdownListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    private static final String DATE_TEMPLATE = "dd/MM/yyyy";
    private String[] headers;
    private String[] cities;
    private String[] genders;
    private DropDownMenu dropDownMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dropDownMenu = findViewById(R.id.dropdown_menu);
        initMenuItems();
        MaskView maskView = findViewById(R.id.mask_view);
        FrameLayout popupParent = findViewById(R.id.popup_parent);
        dropDownMenu.attachViews(maskView, popupParent);
    }

    private void initMenuItems() {
        headers = getResources().getStringArray(R.array.headerList);
        cities = getResources().getStringArray(R.array.cityList);
        genders = getResources().getStringArray(R.array.genderList);

        List<BaseDropdownView> list = new ArrayList<>();
        ListView cityListView = new ListView(this);
        DropdownListAdapter cityListAdapter = new DropdownListAdapter(this, Arrays.asList(cities));
        cityListView.setAdapter(cityListAdapter);
        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dropDownMenu.setMenuItemClicked(0, position, cities[position]);
            }
        });
        list.add(new BaseDropdownView(cityListView, cityListAdapter));

        ListView genderListView = new ListView(this);
        DropdownListAdapter genderListAdapter = new DropdownListAdapter(this, Arrays.asList(genders));
        genderListView.setAdapter(genderListAdapter);
        genderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dropDownMenu.setMenuItemClicked(1, position, genders[position]);
            }
        });
        list.add(new BaseDropdownView(genderListView, genderListAdapter));

        View calendarViewLayout = LayoutInflater.from(this).inflate(R.layout.calendar_view, null);
        io.blackbox_vision.materialcalendarview.view.CalendarView calendarView = calendarViewLayout.findViewById(R.id.calendar_view);
        calendarView.setFirstDayOfWeek(Calendar.SUNDAY).setOnDateClickListener(this::onDateClick);
        list.add(new BaseDropdownView(calendarViewLayout, new CalendarDropdownAdapter()));

        dropDownMenu.setDropDownMenu(Arrays.asList(headers), list );

        dropDownMenu.setOnMenuItemClickListener(this::menuItemClicked);
    }

    private void onDateClick(@NonNull final Date date) {
        //textView.setText(formatDate(DATE_TEMPLATE, date));
        dropDownMenu.setMenuItemClicked(2, 0, date);
    }

    private void menuItemClicked(int menu, int position, Object data) {
        //Toast.makeText(this, "Menu Item Changed " + )

        StringBuffer sb = new StringBuffer();
        sb.append("Selected Menu Items: \n");
        for (int i = 0; i < 3; i++) {
            sb.append(headers[i]).append("=").append(dropDownMenu.getMenuSelectedItem(i)).append("\n");
        }
        Toast.makeText(this, sb, Toast.LENGTH_LONG).show();
    }
}
