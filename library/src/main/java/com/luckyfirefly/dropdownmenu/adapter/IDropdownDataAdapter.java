package com.luckyfirefly.dropdownmenu.adapter;

public interface IDropdownDataAdapter<T> {
    public void setItemSelectedColor(int color);
    public void setItemUnselectedColor(int color);
    public void setSelectedItem(int position, T item);
    public T getSelectedItem();
    public String getFormattedItem();
}
