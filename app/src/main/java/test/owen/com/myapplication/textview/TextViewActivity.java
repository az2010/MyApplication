package test.owen.com.myapplication.textview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.LocaleSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.ScaleXSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import test.owen.com.myapplication.R;

/**
 * Created by owen on 2016/1/6.
 */
public class TextViewActivity extends Activity {

    private static final String TEXT_HELLO = "Hello ";
    private static final String TEXT_ANDROID = "Android";

    private Button mSwitch;
    private TextView mShow;

    private int mCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textview);
        initView();
    }

    private void initView() {
        mSwitch = (Button) findViewById(R.id.switcher);
        mShow = (TextView) findViewById(R.id.textView);

        mSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpannableString spannableString = getSpannableString(mCount % 10);
                mShow.setText(spannableString);
                mCount++;
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private SpannableString getSpannableString(int index) {
        String text = TEXT_HELLO + TEXT_ANDROID;
        SpannableString spannableString = new SpannableString(text);
        switch (index) {
            case 0:
                spannableString.setSpan(new BackgroundColorSpan(Color.BLUE), TEXT_HELLO.length(), text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                showToast("BackgroundColorSpan");
                break;
            case 1:
                spannableString.setSpan(new AbsoluteSizeSpan(30, true), TEXT_HELLO.length(), text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                showToast("AbsoluteSizeSpan");
                break;
            case 2:
                spannableString.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        showToast("on click");
                    }
                }, TEXT_HELLO.length(), text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                showToast("ClickableSpan");
                mShow.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case 3:
                spannableString.setSpan(new DynamicDrawableSpan() {
                    @Override
                    public Drawable getDrawable() {
                        int fontSize = (int) mShow.getTextSize();
                        Rect rect = new Rect(0, 0, fontSize, fontSize);
                        Drawable drawable = TextViewActivity.this.getResources().getDrawable(R.mipmap.f000);
                        drawable.setBounds(rect);
                        return drawable;
                    }
                }, TEXT_HELLO.length(), text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                showToast("DynamicDrawableSpan");
                break;
            case 4:
                spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), TEXT_HELLO.length(), text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                showToast("ForegroundColorSpan");
                break;
            case 5:
                Drawable drawable = TextViewActivity.this.getResources().getDrawable(R.mipmap.f000);
                spannableString.setSpan(new ImageSpan(drawable), TEXT_HELLO.length(), text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                showToast("ImageSpan");
                break;
            case 6:
                spannableString.setSpan(new LocaleSpan(Locale.CHINA), TEXT_HELLO.length(), text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                showToast("LocaleSpan");
                break;
            case 7:
                spannableString.setSpan(new MaskFilterSpan(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL)), TEXT_HELLO.length(), text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                showToast("MaskFilterSpan->BlurMaskFilter");
                break;
            case 8:
                float[] direction = new float[]{10, 10, 10};
                spannableString.setSpan(new MaskFilterSpan(new EmbossMaskFilter(direction, 0.5f, 8f, 3.5f)), TEXT_HELLO.length(), text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                showToast("MaskFilterSpan->EmbossMaskFilter");
                break;
            case 9:
                spannableString.setSpan(new ScaleXSpan(0.5f), TEXT_HELLO.length(), text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                showToast("ScaleXSpan");
                break;
            default:
                break;
        }
        return spannableString;
    }

    private void showToast(String text) {
        Toast.makeText(TextViewActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
