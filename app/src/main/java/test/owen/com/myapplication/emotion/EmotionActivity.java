package test.owen.com.myapplication.emotion;

import android.app.Activity;
import android.os.Bundle;

import test.owen.com.myapplication.R;

/**
 * Created by owen on 2016/1/6.
 */
public class EmotionActivity extends Activity {

    private SimpleEmTextView mSimpleEmTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emotion);
        initView();
    }

    private void initView() {
        mSimpleEmTextView = (SimpleEmTextView) findViewById(R.id.emotion);
        mSimpleEmTextView.setText("[em]e113[/em],[em]e198[/em],[em]e202[/em]");
    }
}
