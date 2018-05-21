package com.luckyfirefly.dropdownmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MenuBar extends LinearLayout {
    private int textSelectedColor = Color.WHITE;
    //tab未选中颜色
    private int textUnselectedColor = Color.BLACK;
    //tab字体大小
    private int menuTextSize = 16;

    //tab选中图标
    private int menuSelectedIcon;
    //tab未选中图标
    private int menuUnselectedIcon;

    private int selectedBackgroundDrawable = R.drawable.menu_selected_drawable;

    //分割线颜色
    private int dividerColor = Color.WHITE;

    //tabMenuView里面选中的tab位置，-1表示未选中
    private int current_tab_position = -1;

    private List<TextView> textViewList = new ArrayList<>();

    private OnMenuSelectedListener onMenuItemClickedListener;

    public MenuBar(Context context) {
        super(context, null);
    }

    public MenuBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DropDownMenuBar);
        textSelectedColor = a.getColor(R.styleable.DropDownMenuBar_mbTextSelectedColor, textSelectedColor);
        textUnselectedColor = a.getColor(R.styleable.DropDownMenuBar_mbTextUnselectedColor, textUnselectedColor);
        menuTextSize = a.getDimensionPixelSize(R.styleable.DropDownMenuBar_mbTextSize, menuTextSize);
        menuSelectedIcon = a.getResourceId(R.styleable.DropDownMenuBar_mbSelectedIcon, menuSelectedIcon);
        menuUnselectedIcon = a.getResourceId(R.styleable.DropDownMenuBar_mbUnselectedIcon, menuUnselectedIcon);
        dividerColor = a.getColor(R.styleable.DropDownMenuBar_mbDividerColor, dividerColor);
        selectedBackgroundDrawable = a.getResourceId(R.styleable.DropDownMenuBar_mbSelectedBackgroundDrawable, selectedBackgroundDrawable);
        a.recycle();
    }

    public void setMenuItems(List<String> items) {
        if (textViewList.size() == items.size()) {
            for (int i = 0; i < textViewList.size(); i++) {
                textViewList.get(i).setText(items.get(i));
            }
        } else {
            removeAllViews();
            textViewList.clear();
            if (items.size() > 0) {
                for (int i = 0; i < items.size(); i++) {
                    final TextView textView = new TextView(getContext());
//                textViewList.add(textView);
                    setDefaultAttributes(textView);
                    textView.setText(items.get(i));
                    addView(textView);
                }
            }
        }
    }

    private void setDefaultAttributes(final TextView textView) {
        textView.setSingleLine();
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
        textView.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        textView.setTextColor(textUnselectedColor);
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuUnselectedIcon), null);
        textView.setPadding(DeviceUtils.dpTpPx(getContext(), 5), DeviceUtils.dpTpPx(getContext(), 8), DeviceUtils.dpTpPx(getContext(), 5), DeviceUtils.dpTpPx(getContext(), 8));
        // add click listener
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMenu(textView);
            }
        });
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof TextView) {
            final TextView textView = (TextView) child;
            if (!textViewList.contains(textView)) {
                textViewList.add(textView);
            }
            setDefaultAttributes(textView);
            // add seperator
            if (getChildCount() > 0) {
                View view = new View(getContext());
                view.setLayoutParams(new LayoutParams(DeviceUtils.dpTpPx(getContext(), 0.5f), ViewGroup.LayoutParams.MATCH_PARENT));
                view.setBackgroundColor(dividerColor);
                super.addView(view, -1);
            }
            super.addView(child, index * 2, params);
        } else {
            super.addView(child, index, params);
        }
    }

    public void setMenuText(int index, String text) {
        TextView textView = textViewList.get(index);
        textView.setText(text);
    }

    public void setOnMenuItemClickedListener(OnMenuSelectedListener listener) {
        this.onMenuItemClickedListener = listener;
    }

    public void resetMenu() {
        if (current_tab_position != -1) {
            unselect(textViewList.get(current_tab_position));
            current_tab_position = -1;
        }
    }

    private void select(TextView textView) {
        textView.setBackgroundResource(selectedBackgroundDrawable);
        //textView.setBackgroundColor(Color.BLUE);
        textView.setTextColor(textSelectedColor);
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null,
                getResources().getDrawable(menuSelectedIcon), null);
    }

    private void unselect(TextView textView) {
        textView.setBackgroundResource(0);
        //textView.setBackgroundColor(Color.TRANSPARENT);
        textView.setTextColor(textUnselectedColor);
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null,
                getResources().getDrawable(menuUnselectedIcon), null);
    }

    private void switchMenu(View target) {
        int selectIndex = -1;
        TextView selectedView = null;
        for (int i = 0; i < textViewList.size(); i++) {
            TextView textView = textViewList.get(i);
            if (target != textView) {
                unselect(textView);
            } else {
                selectIndex = i;
                selectedView = textView;
            }
        }
        if (current_tab_position == selectIndex) {
            unselect(selectedView);
            if (onMenuItemClickedListener != null && current_tab_position != -1) {
                onMenuItemClickedListener.onMenuClose(current_tab_position);
            }
            current_tab_position = -1;
        } else {
            current_tab_position = selectIndex;
            select(selectedView);
            if (onMenuItemClickedListener != null) {
                onMenuItemClickedListener.onMenuOpen(current_tab_position);
            }
        }
    }

    public interface OnMenuSelectedListener {
        public void onMenuOpen(int position);

        public void onMenuClose(int position);
    }
}
