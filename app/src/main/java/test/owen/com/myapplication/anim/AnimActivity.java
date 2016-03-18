package test.owen.com.myapplication.anim;


import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import test.owen.com.myapplication.LogUtils;
import test.owen.com.myapplication.R;

public class AnimActivity extends Activity {

    private Button mRunButton;
    private Button mStartButton;
    private Button mEndButton;
    private float mOffsetY;
    private float mOffsetX;
    private float mFactor;
    private boolean mUp = false;
    private ValueAnimator mAnimator;

    private HandlerThread mHandlerThread;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim);
        initView();
        initHandlerThread();
        LogUtils.log("azheng", "onCreate->");
    }

    private void initHandlerThread() {
        mHandlerThread = new HandlerThread("anim thread");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    private void initView() {
        mRunButton = (Button) findViewById(R.id.anim_run);
        mStartButton = (Button) findViewById(R.id.anim_start);
        mEndButton = (Button) findViewById(R.id.anim_end);

        mRunButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mOffsetX == 0) {
                    initData();
                    initAnim();
                }
                if (mAnimator.isRunning()) {
                    return;
                }
                mUp = !mUp;
                start();
            }
        });
    }

    private void start() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mAnimator.start();
            }
        });
    }

    private void initData() {
        mOffsetX = mEndButton.getX();
        mOffsetY = mStartButton.getY();
        mFactor = mOffsetY / (mOffsetX * mOffsetX);
    }

    private void initAnim() {
        mAnimator = ValueAnimator.ofFloat(0f, mOffsetX);
        mAnimator.setDuration(3000);

        mAnimator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                update(animation);
            }
        });
    }

    private void update(final ValueAnimator animation) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                float x;
                float y;
                float offsetX = (Float) animation.getAnimatedValue();
                float offsetY = mFactor * offsetX * offsetX;
                if (offsetX == 0) {
                    return;
                }
                if (mUp) {
                    x = offsetX;
                    y = mOffsetY - offsetY;
                } else {
                    x = mOffsetX - offsetX;
                    y = offsetY;
                }
                mStartButton.setX(x);
                mStartButton.setY(y);
                mStartButton.setRotation(1080 * offsetX / mOffsetX);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandlerThread.quit();
    }
}
