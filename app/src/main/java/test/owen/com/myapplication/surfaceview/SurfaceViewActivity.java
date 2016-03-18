package test.owen.com.myapplication.surfaceview;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import test.owen.com.myapplication.R;

/**
 * Created by owen on 2016/2/2.
 */
public class SurfaceViewActivity extends Activity {

    private SurfaceHolder mSurfaceHolder;
    private boolean mRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surface_view);
        initView();
    }

    private void initView() {
        mRunning = true;
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        mSurfaceHolder = surfaceView.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                new Thread(){
                    public void run() {
                        while (mRunning) {
                            Canvas canvas = mSurfaceHolder.lockCanvas(new Rect(0, 0, 200, 200));
                            Paint paint = new Paint();
                            paint.setColor(Color.rgb((int) (Math.random() * 255),
                                    (int) (Math.random() * 255), (int) (Math.random() * 255)));
                            Rect aa = new Rect((int) (Math.random() * 100),
                                    (int) (Math.random() * 100)
                                    , (int) (Math.random() * 500)
                                    , (int) (Math.random() * 500));
                            canvas.drawRect(aa, paint);
                            mSurfaceHolder.unlockCanvasAndPost(canvas);
                            try {
                                Thread.sleep(3000);
                            } catch (Exception e) {
                            }
                        }
                    };
                }.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mRunning = false;
            }
        });
    }

}
