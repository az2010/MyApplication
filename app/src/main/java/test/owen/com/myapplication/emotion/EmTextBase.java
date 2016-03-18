package test.owen.com.myapplication.emotion;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.regex.Matcher;

import test.owen.com.myapplication.emotion.base.EmParser;
import test.owen.com.myapplication.emotion.base.IEmCallback;
import test.owen.com.myapplication.emotion.base.StickerSpan;

/**
 * Created by winghe on 2015/11/23.
 */
public class EmTextBase extends SafeTextView implements IEmCallback {
    private ArrayList<EmParser> mParsers = new ArrayList<EmParser>();
    private CharSequence mOriginText;

    public EmTextBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void addParser(EmParser parser) {
        mParsers.add(parser);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        mOriginText = text;
        if (TextUtils.isEmpty(text) || mParsers == null || mParsers.size() < 1) {
            super.setText(text, type);
            return;
        }
        int fontSize = (int) getTextSize();
        final Rect rect = new Rect(0, 0, fontSize, fontSize);
        SpannableString span = new SpannableString(text);
        for (int i = 0; i < mParsers.size(); i++) {
            EmParser parser = mParsers.get(i);
            Matcher m = parser.pattern.matcher(text);
            while (m.find()) {
                String code = m.group(parser.index);
                Drawable drawable = parser.filter.filter(code);
                if (drawable != null) {
                    drawable.setBounds(rect);
                    StickerSpan imgSpan = new StickerSpan(DynamicDrawableSpan.ALIGN_BOTTOM, drawable);
                    int start = m.start();
                    int end = m.end();
                    span.setSpan(imgSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        try {
            super.setText(span, type);
        } catch (Exception e) {
            super.setText(text, type);
        }
    }

    @Override
    public void onEmotionLoad() {
        setText(mOriginText);
    }

}