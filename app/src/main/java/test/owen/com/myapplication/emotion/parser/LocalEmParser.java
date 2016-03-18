package test.owen.com.myapplication.emotion.parser;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.lang.ref.WeakReference;

import test.owen.com.myapplication.emotion.EmCache;
import test.owen.com.myapplication.emotion.base.EmConfig;
import test.owen.com.myapplication.emotion.base.IEmParser;

/**
 * Created by winghe on 2015/12/1.
 */
public class LocalEmParser implements IEmParser {

    private WeakReference<Context> mContextRef;

    public LocalEmParser(Context context){
        mContextRef = new WeakReference<Context>(context);
    }

    @Override
    public Drawable filter(String code) {
        if(mContextRef == null){
            return null;
        }

        Drawable drawable = EmCache.getInstance().get(code);
        if(drawable != null){
            return drawable;
        }

        Context context = mContextRef.get();
        if(context == null){
            return null;
        }

        int index = EmConfig.getSmileyIndex("[em]" + code + "[/em]");
        if (index > -1 && index < EmConfig.EMO_FAST_SYMBOL_QZONE.length) {
            drawable = EmConfig.getLocalEmoDrawable(index, context);
            EmCache.getInstance().put(code, drawable);
        }
        return drawable;
    }

}