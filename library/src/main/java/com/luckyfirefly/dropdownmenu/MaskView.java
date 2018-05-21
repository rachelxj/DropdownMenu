package com.luckyfirefly.dropdownmenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MaskView extends View {
    private Bitmap barImage;
    private int x;
    private int y;

    public MaskView(Context context) {
        super(context, null);
    }

    public MaskView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaskView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBarImage(Bitmap bitmap, int x, int y) {
        this.barImage = bitmap;
        this.x = x;
        this.y = y;
    }
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (barImage != null && !barImage.isRecycled()) {
            RectF r = new RectF(x,y, x + barImage.getWidth(), y + barImage.getHeight());
            canvas.clipRect(r);
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            canvas.drawRoundRect(r, 5, 5, paint);
            canvas.drawBitmap(barImage, x, y, null);
        }
    }
}
