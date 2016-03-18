package test.owen.com.myapplication.emotion.base;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;

/**
 * Created by winghe on 2015/8/5.
 */
public class StickerSpan extends DynamicDrawableSpan {
    Drawable mDrawable;

    public StickerSpan(int verticalAlignment){
        super(verticalAlignment);
    }

    public StickerSpan(int verticalAlignment, Drawable drawable){
        super(verticalAlignment);
        mDrawable = drawable;
    }

    @Override
    public Drawable getDrawable() {
        return mDrawable;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text,
                     int start, int end, float x,
                     int top, int y, int bottom, Paint paint) {
        Drawable b = getDrawable();
        canvas.save();

        Paint.FontMetricsInt font = paint.getFontMetricsInt();
        int iconHeight = b.getBounds().bottom;
//        int fontHeight = font.descent - font.top;
//        int fontBottom = top + fontHeight;
//        int diff = fontHeight - iconHeight;
//        int iconTop = fontBottom - diff / 2 - iconHeight;  // iconTop = (top + font.descent - font.top) - (font.descent - font.top - iconHeight) / 2 - iconHeight = top + font.descent/2 - font.top/2 - iconHeight / 2

        int iconTop = top + (font.descent - font.top - iconHeight) / 2;

        if(iconTop + iconHeight > bottom){
            iconTop = bottom - iconHeight;
        }
        canvas.translate(x, iconTop);
        b.draw(canvas);
        canvas.restore();
    }
}