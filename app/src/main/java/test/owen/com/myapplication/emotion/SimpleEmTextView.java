package test.owen.com.myapplication.emotion;

import android.content.Context;
import android.util.AttributeSet;

import test.owen.com.myapplication.emotion.base.EmConfig;
import test.owen.com.myapplication.emotion.base.EmParser;
import test.owen.com.myapplication.emotion.parser.LocalEmParser;

/**
 * Created by winghe on 2015/12/1.
 */
public class SimpleEmTextView extends EmTextBase {
    public SimpleEmTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addParser(new EmParser(EmConfig.SMILEY_PATTERN, new LocalEmParser(getContext())));
    }
}
