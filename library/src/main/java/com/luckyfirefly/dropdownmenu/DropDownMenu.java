package com.luckyfirefly.dropdownmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.luckyfirefly.dropdownmenu.adapter.IDropdownView;

import java.util.List;

public class DropDownMenu extends LinearLayout implements MenuBar.OnMenuSelectedListener {
    // mask background
    private int maskColor = 0x88888888;

    private float menuHeighPercent = 0.5f;

    private int menuWidth = ViewGroup.LayoutParams.MATCH_PARENT;

    private MenuBar menuBar;

    private int menuBarScreenX;
    private int menuBarScreenY;
    // menu container
    private FrameLayout popupMenuContainer;

    private MaskView maskView;

    private List<? extends IDropdownView> dropdownMenus;

    private OnMenuItemClickListener onMenuItemClickListener;

    public DropDownMenu(Context context) {
        super(context, null);
    }

    public DropDownMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropDownMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DropDownMenu);
        maskColor = a.getColor(R.styleable.DropDownMenu_menuMaskColor, maskColor);
        menuHeighPercent = a.getFloat(R.styleable.DropDownMenu_menuHeightPercent, menuHeighPercent);
        menuWidth = a.getDimensionPixelSize(R.styleable.DropDownMenu_menuWidth, menuWidth);

        a.recycle();
    }

    public void attachViews(MaskView maskView, FrameLayout popupParent) {
        this.maskView = maskView;
        maskView.setBackgroundColor(maskColor);
        maskView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!isTouchPointInMenuBar((int) event.getX(), (int) event.getY())) {
                    closeMenu();
                    return true;
                }
                return false;
            }
        });
        this.popupMenuContainer = popupParent;
    }

    private boolean isTouchPointInMenuBar(int x, int y) {
        int[] location = new int[2];
        getLocationInScreen(menuBar, location);
        int left = location[0];
        int top = location[1];
        int right = left + menuBar.getMeasuredWidth();
        int bottom = top + menuBar.getMeasuredHeight();
        //view.isClickable() &&
        if (y >= top && y <= bottom && x >= left
                && x <= right) {
            return true;
        }
        return false;
    }

    public void setDropDownMenu(final List<String> headers, final List<? extends IDropdownView> items) {
        if (headers.size() != items.size()) {
            throw new IllegalArgumentException("header's size must equal to item's size");
        }
        this.dropdownMenus = items;
        menuBar.setMenuItems(headers);
        menuBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                menuBar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int count = items.size();
                for (int i = 0; i < items.size(); i++) {
                    final IDropdownView item = items.get(i);
                    int layoutWidth = item.getDropdownWidth();
                    if (layoutWidth <= 0) {
                        layoutWidth = menuWidth;
                    }
                    if (layoutWidth != ViewGroup.LayoutParams.MATCH_PARENT) {
                        layoutWidth = DeviceUtils.dpTpPx(getContext(), layoutWidth);
                    }
                    RelativeLayout layout = new RelativeLayout(getContext());
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    int left = menuBar.getLeft() + i * (menuBar.getWidth() / count);
                    int[] location = new int[2];
                    //menuBar.getLocationOnScreen(location);
                    getLocationInScreen(menuBar, location);
                    menuBarScreenX = location[0];
                    menuBarScreenY = location[1];
                    int top = menuBarScreenY + menuBar.getHeight();
                    left += menuBarScreenX;
                    if (i < count - 1) {
                        layoutParams.setMargins(left, top, 0, 0);
                    } else {
                        int minLeft = menuBar.getRight() - layoutWidth + location[0];
                        if (minLeft < left) {
                            left = minLeft;
                        }
                        layoutParams.setMargins(left, top, 0, 0);
                    }
                    layout.setLayoutParams(layoutParams);

                    item.getDropdownView().setLayoutParams(new ViewGroup.LayoutParams(layoutWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
                    layout.addView(item.getDropdownView());
                    layout.setVisibility(GONE);
                    popupMenuContainer.addView(layout, i);
                }
            }
        });
    }
    private void getLocationInScreen(View view, int[] locations) {
        int[] current = new int[2];

        view.getLocationOnScreen(locations);

        while (view.getParent() instanceof View) {
            view = (View) view.getParent();

            if ("MenuBarRootView".equals(view.getTag())) {
                view.getLocationInWindow(current);
                locations[0] -= current[0];
                locations[1] -= current[1];
                break;
            }
        }
    }
    public void setMenuItemClicked(int menu, int position, Object data) {
        dropdownMenus.get(menu).getDataAdapter().setSelectedItem(position, data);
        if (data != null) {
            menuBar.setMenuText(menu, dropdownMenus.get(menu).getDataAdapter().getFormattedItem());
        }
        closeMenu();
        if (onMenuItemClickListener != null) {
            onMenuItemClickListener.onMenuItemClicked(menu, position, data);
        }
    }

    public Object getMenuSelectedItem(int menu) {
        return dropdownMenus.get(menu).getDataAdapter().getSelectedItem();
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof MenuBar) {
            menuBar = (MenuBar) child;
            menuBar.setOnMenuItemClickedListener(this);
        }
        super.addView(child, index, params);
    }

    private void closeMenu() {
        if (maskView != null) {
            maskView.setVisibility(GONE);
            maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_out));
        }
        menuBar.resetMenu();
        menuBar.destroyDrawingCache();
        if (popupMenuContainer != null) {
            popupMenuContainer.setVisibility(View.GONE);
        }
    }


    @Override
    public void onMenuOpen(int position) {
        if (popupMenuContainer != null) {
            for (int i = 0; i < popupMenuContainer.getChildCount(); i++) {
                if (i != position) {
                    popupMenuContainer.getChildAt(i).setVisibility(View.GONE);
                }
            }
            if (position < popupMenuContainer.getChildCount()) {
                popupMenuContainer.setVisibility(View.VISIBLE);
                popupMenuContainer.getChildAt(position).setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_in));
                popupMenuContainer.getChildAt(position).setVisibility(View.VISIBLE);
            }
        }
        if (maskView != null) {
            menuBar.setDrawingCacheEnabled(true);
            menuBar.buildDrawingCache();
            Bitmap bmp = menuBar.getDrawingCache();
            maskView.setBarImage(bmp, menuBarScreenX, menuBarScreenY);
            maskView.setVisibility(View.VISIBLE);
            maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_in));
        }
    }

    @Override
    public void onMenuClose(int position) {
        closeMenu();
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        onMenuItemClickListener = listener;
    }
    public interface OnMenuItemClickListener {
        public void onMenuItemClicked(int menu, int position, Object data);
    }
}
