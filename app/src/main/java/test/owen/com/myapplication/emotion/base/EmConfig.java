package test.owen.com.myapplication.emotion.base;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.regex.Pattern;

import test.owen.com.myapplication.R;

/**
 * Created by winghe on 2015/12/1.
 */
public class EmConfig {
    public static final String SMILEY_PATTERN_STRING = "\\[em\\](e\\d{1,})\\[/em\\]";

    public static final Pattern SMILEY_PATTERN = Pattern.compile(SMILEY_PATTERN_STRING, Pattern.CASE_INSENSITIVE);

    public static final String[] EMO_FAST_SYMBOL_QZONE = {
            "[em]e113[/em]", "[em]e112[/em]", "[em]e127[/em]", "[em]e120[/em]", "[em]e139[/em]", "[em]e138[/em]",
            "[em]e140[/em]", "[em]e162[/em]", "[em]e163[/em]", "[em]e105[/em]", "[em]e109[/em]", "[em]e133[/em]",
            "[em]e116[/em]", "[em]e118[/em]", "[em]e149[/em]", "[em]e174[/em]", "[em]e170[/em]", "[em]e155[/em]",
            "[em]e121[/em]", "[em]e102[/em]", "[em]e106[/em]", "[em]e104[/em]", "[em]e119[/em]", "[em]e100[/em]",
            "[em]e111[/em]", "[em]e110[/em]", "[em]e126[/em]", "[em]e117[/em]", "[em]e166[/em]", "[em]e165[/em]",
            "[em]e122[/em]", "[em]e123[/em]", "[em]e115[/em]", "[em]e114[/em]", "[em]e132[/em]", "[em]e108[/em]",
            "[em]e152[/em]", "[em]e128[/em]", "[em]e190[/em]", "[em]e136[/em]", "[em]e101[/em]", "[em]e151[/em]",
            "[em]e130[/em]", "[em]e103[/em]", "[em]e146[/em]", "[em]e178[/em]", "[em]e144[/em]", "[em]e191[/em]",
            "[em]e148[/em]", "[em]e134[/em]", "[em]e129[/em]", "[em]e154[/em]", "[em]e179[/em]", "[em]e180[/em]",
            "[em]e181[/em]", "[em]e182[/em]", "[em]e183[/em]", "[em]e164[/em]", "[em]e161[/em]", "[em]e168[/em]",
            "[em]e156[/em]", "[em]e157[/em]", "[em]e173[/em]", "[em]e184[/em]", "[em]e189[/em]", "[em]e187[/em]",
            "[em]e160[/em]", "[em]e175[/em]", "[em]e171[/em]", "[em]e193[/em]", "[em]e186[/em]", "[em]e185[/em]",
            "[em]e167[/em]", "[em]e176[/em]", "[em]e177[/em]", "[em]e172[/em]", "[em]e137[/em]", "[em]e199[/em]",
            "[em]e169[/em]", "[em]e124[/em]", "[em]e125[/em]", "[em]e131[/em]", "[em]e135[/em]", "[em]e141[/em]",
            "[em]e142[/em]", "[em]e143[/em]", "[em]e145[/em]", "[em]e147[/em]", "[em]e150[/em]", "[em]e153[/em]",
            "[em]e158[/em]", "[em]e159[/em]", "[em]e188[/em]", "[em]e192[/em]", "[em]e194[/em]", "[em]e195[/em]",
            "[em]e196[/em]", "[em]e197[/em]", "[em]e198[/em]", "[em]e200[/em]", "[em]e201[/em]", "[em]e202[/em]",
            "[em]e203[/em]", "[em]e204[/em]", "[em]e107[/em]"
    };

    private static HashMap<String, Integer> sSmileyHashMap;

    static {
        initSmileyHashMap();
    }

    private static void initSmileyHashMap() {
        sSmileyHashMap = new HashMap<String, Integer>();
        for (int i = 0; i < EMO_FAST_SYMBOL_QZONE.length; i++)
            sSmileyHashMap.put(EMO_FAST_SYMBOL_QZONE[i], Integer.valueOf(i));

    }

    public static int getSmileyIndex(String mStr) {
        int ret = -1;

        if (sSmileyHashMap != null) {
            Integer value = sSmileyHashMap.get(mStr);
            if (value != null)
                ret = value.intValue();
        }
        return ret;
    }


    /**
     * 只处理本地表情
     *
     * @param pos
     * @param context
     * @return
     */
    public static Drawable getLocalEmoDrawable(int pos, Context context) {
        try {
            if ((pos >= 0 && pos < EMO_FAST_SYMBOL_QZONE.length)) {
                int id = R.mipmap.f000 + pos;
                Drawable d = context.getResources().getDrawable(id);
                return d;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
