package com.luckyfirefly.sample;

import com.luckyfirefly.dropdownmenu.adapter.IDropdownDataAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarDropdownAdapter implements IDropdownDataAdapter<Date> {
    private Date date = Calendar.getInstance().getTime();
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

    public CalendarDropdownAdapter() {

    }

    @Override
    public void setItemSelectedColor(int color) {

    }

    @Override
    public void setItemUnselectedColor(int color) {

    }

    @Override
    public void setSelectedItem(int position, Date item) {
        this.date = item;
    }

    @Override
    public Date getSelectedItem() {
        return date;
    }

    @Override
    public String getFormattedItem() {
        return date != null ? SDF.format(date) : "";
    }
}
