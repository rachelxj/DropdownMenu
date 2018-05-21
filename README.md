# DropdownMenu
With a bigger screen on tablet than on a phone, I want to create a Menubar to put on the view without taking the whole area on the screen, and each menu item will have its own width. It looks like a dropdown menu on a web page, easy to use, yet with a little constraints in order to implement a full screen mask. 

# Screenshot
![](https://github.com/rachelxj/DropdownMenu/blob/master/Screenshot/device-2018-05-21-165323.gif)

# Usage
* Add dropdown menu in layout file
```
    <com.luckyfirefly.dropdownmenu.DropDownMenu
        android:id="@+id/dropdown_menu"
        android:layout_width="600dp"
        android:layout_height="match_parent"
        android:orientation="vertical"
        app:menuWidth="400dp">

        <com.luckyfirefly.dropdownmenu.MenuBar
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_gravity="center"
            android:background="@drawable/shape_corner"
            android:gravity="center"
            android:orientation="horizontal"
            app:mbSelectedIcon="@mipmap/drop_down_selected_icon"
            app:mbTextSize="24sp"
            app:mbUnselectedIcon="@mipmap/drop_down_unselected_icon">

            <TextView
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="@string/city" />

            <TextView
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="@string/gender" />

            <TextView
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="@string/start_time" />

        </com.luckyfirefly.dropdownmenu.MenuBar>
    </com.luckyfirefly.dropdownmenu.DropDownMenu>
```
* Add mask, popup parent view on root view, root view must use FrameLayout. Add one special Tag "MenuBarRootView" on root layout, which is used to calculate popup location
```
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    <red>android:tag="MenuBarRootView"</red>
    tools:context=".MainActivity">
    <Line<br>rLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent" android:orientation="vertical">
        <TextView
            android:id="@+id/textview"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Hello World!" />

        <include layout="@layout/menubar_sample" />

    </LinearLayout>
    `<com.luckyfirefly.dropdownmenu.MaskView
        android:id="@+id/mask_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#88888888"
        android:visibility="gone">

    </com.luckyfirefly.dropdownmenu.MaskView>

    <FrameLayout
        android:id="@+id/popup_parent"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

    </FrameLayout>`
</FrameLayout>
```



# Acknowledge
Thanks to [dongjunkun/DropDownMenu](https://github.com/dongjunkun/DropDownMenu)

----
# 中文
为了实现一个能在平板上放置的多条件筛选下拉菜单（不需要全屏显示），学习了[dongjunkun/DropDownMenu](https://github.com/dongjunkun/DropDownMenu)筛选菜单的实现，重构了代码，将菜单标题栏实现分离，并可以在平板等设备上任意位置放置菜单栏，可分别设置下拉菜单的宽度，并设置全屏遮罩。实现了网页下拉菜单的效果。可在布局文件中直接定义标题栏，方便以后扩展

# 截图
![](https://github.com/rachelxj/DropdownMenu/blob/master/Screenshot/device-2018-05-21-165323.gif)

# 使用方法
* 在布局文件中定义下拉菜单
```
    <com.luckyfirefly.dropdownmenu.DropDownMenu
        android:id="@+id/dropdown_menu"
        android:layout_width="600dp"
        android:layout_height="match_parent"
        android:orientation="vertical"
        app:menuWidth="400dp">

        <com.luckyfirefly.dropdownmenu.MenuBar
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_gravity="center"
            android:background="@drawable/shape_corner"
            android:gravity="center"
            android:orientation="horizontal"
            app:mbSelectedIcon="@mipmap/drop_down_selected_icon"
            app:mbTextSize="24sp"
            app:mbUnselectedIcon="@mipmap/drop_down_unselected_icon">

            <TextView
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="@string/city" />

            <TextView
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="@string/gender" />

            <TextView
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="@string/start_time" />

        </com.luckyfirefly.dropdownmenu.MenuBar>
    </com.luckyfirefly.dropdownmenu.DropDownMenu>
```
* 在最上层的布局中添加遮罩及弹出窗体父布局对象, 根布局必须采用FrameLayout. 在根布局中添加标签"MenuBarRootView"，该标签用来计算弹出窗体的位置
```
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    `android:tag="MenuBarRootView"`
    tools:context=".MainActivity">
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent" android:orientation="vertical">
        <TextView
            android:id="@+id/textview"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Hello World!" />

        <include layout="@layout/menubar_sample" />

    </LinearLayout>
    `<com.luckyfirefly.dropdownmenu.MaskView
        android:id="@+id/mask_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#88888888"
        android:visibility="gone">

    </com.luckyfirefly.dropdownmenu.MaskView>

    <FrameLayout
        android:id="@+id/popup_parent"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

    </FrameLayout>`
</FrameLayout>
```
# 感谢
感谢[dongjunkun/DropDownMenu](https://github.com/dongjunkun/DropDownMenu)
