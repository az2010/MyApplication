package test.owen.com.myapplication.anim;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import test.owen.com.myapplication.R;
import test.owen.com.myapplication.view.View360;


public class AnimActivity2 extends Activity {

    private View360 mView360;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim2);
        initView();
    }

    private void initView() {
        mView360 = (View360) findViewById(R.id.anim2);
        View run = findViewById(R.id.run);
        View stop = findViewById(R.id.stop);

        run.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mView360.start();
            }
        });
        stop.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mView360.stop();
            }
        });
    }

}
