package com.luckyfirefly.dropdownmenu.adapter;

import android.view.View;

public class BaseDropdownView implements IDropdownView {
    private final View view;
    private final IDropdownDataAdapter adapter;
    private final int dropdownWidth;

    public BaseDropdownView(View view, IDropdownDataAdapter adapter) {
        this(view, adapter, 0);
    }

    public BaseDropdownView(View view, IDropdownDataAdapter adapter, int dropdownWidth) {
        this.view = view;
        this.adapter = adapter;
        this.dropdownWidth = dropdownWidth;
    }

    @Override
    public View getDropdownView() {
        return view;
    }

    @Override
    public IDropdownDataAdapter getDataAdapter() {
        return adapter;
    }

    @Override
    public int getDropdownWidth() {
        return dropdownWidth;
    }
}
