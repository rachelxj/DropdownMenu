package com.luckyfirefly.dropdownmenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.luckyfirefly.dropdownmenu.R;

import java.util.List;

public class DropdownListAdapter extends BaseAdapter implements IDropdownDataAdapter<String> {

    private Context context;
    private List<String> list;
    private int checkItemPosition = 0;

    private int itemSelectedColor;
    private int itemUnselectedColor;

    public DropdownListAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        this.itemSelectedColor = context.getResources().getColor(R.color.drop_down_selected);
        this.itemUnselectedColor = context.getResources().getColor(R.color.drop_down_unselected);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public String getSelectedItem() {
        return list.get(checkItemPosition);
    }

    @Override
    public String getFormattedItem() {
        return getSelectedItem();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_default_drop_down, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, ViewHolder viewHolder) {
        viewHolder.mText.setText(list.get(position));
        if (checkItemPosition != -1) {
            if (checkItemPosition == position) {
                viewHolder.mText.setTextColor(itemSelectedColor);
                viewHolder.mText.setBackgroundResource(R.color.check_bg);
            } else {
                viewHolder.mText.setTextColor(itemUnselectedColor);
                viewHolder.mText.setBackgroundResource(R.color.white);
            }
        }
    }

    @Override
    public void setItemSelectedColor(int color) {
        this.itemSelectedColor = color;
    }

    @Override
    public void setItemUnselectedColor(int color) {
        this.itemUnselectedColor = color;
    }

    @Override
    public void setSelectedItem(int position, String item) {
        if (position < getCount()) {
            checkItemPosition = position;
            notifyDataSetChanged();
        }
    }

    static class ViewHolder {
        TextView mText;

        ViewHolder(View view) {
            this.mText = view.findViewById(R.id.text);
        }
    }
}
