package test.owen.com.myapplication.svg;

import android.app.Activity;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import test.owen.com.myapplication.R;

/**
 * Created by owen on 2016/2/18.
 */
public class SvgActivity extends Activity {

    private int[] mSvgIds;
    private int mIndex = 0;
    private PathView mPathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.svg);
        mPathView = (PathView) findViewById(R.id.pathView);
//        final Path path = makeConvexArrow(50, 100);
//        mPathView.setPath(path);
        mPathView.setFillAfter(true);
        mPathView.useNaturalColors();
        mPathView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawSvg();
            }
        });
        mSvgIds = new int[]{R.raw.flag_usa, R.raw.ironman, R.raw.ironman_white, R.raw.issues,
                R.raw.linecap, R.raw.logout, R.raw.map_usa, R.raw.monitor, R.raw.settings};
    }

    private void drawSvg() {
//        mPathView.setSvgResource(getSvgId());
        mPathView.getPathAnimator().
                delay(100).
                duration(1500).
                interpolator(new AccelerateDecelerateInterpolator()).
                start();
    }

    private int getSvgId() {
        if (mIndex >= mSvgIds.length) {
            mIndex = 0;
        }
        int id = mSvgIds[mIndex];
        mIndex++;
        return id;
    }

    private Path makeConvexArrow(float length, float height) {
        final Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.lineTo(length / 4f, 0.0f);
        path.lineTo(length, height / 2.0f);
        path.lineTo(length / 4f, height);
        path.lineTo(0.0f, height);
        path.lineTo(length * 3f / 4f, height / 2f);
        path.lineTo(0.0f, 0.0f);
        path.close();
        return path;
    }
}
