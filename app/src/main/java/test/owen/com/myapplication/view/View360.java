package test.owen.com.myapplication.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import test.owen.com.myapplication.LogUtils;
import test.owen.com.myapplication.R;


public class View360 extends View {

    private int mWidth;
    private int mHeight;
    private boolean mRunning;
    private Paint mPaint;

    private ArrayList<AnimatorHolder> mAnimPool;
    private HashMap<String, CircleView> mCircleMap;

    private static final float RADIUS = 40f;

    public View360(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAnimPool = new ArrayList<AnimatorHolder>();
        mCircleMap = new HashMap<String, CircleView>();

        mPaint = new Paint();
        mPaint.setColor(getContext().getResources().getColor(android.R.color.holo_green_dark));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = right - left;
        mHeight = bottom - top;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mWidth <= 0 || mHeight <= 0) {
            return;
        }
        canvas.drawColor(getContext().getResources().getColor(R.color.galler_bg));
        synchronized (mPaint) {
            Iterator<String> iter = mCircleMap.keySet().iterator();
            while (iter.hasNext()) {
                CircleView circleView = mCircleMap.get(iter.next());
                mPaint.setAlpha(circleView.mAlpha);
                canvas.drawCircle(circleView.mCurX, circleView.mCurY, circleView.mRadius, mPaint);
            }
        }
        if (!mCircleMap.isEmpty()) {
            postInvalidateDelayed(30);
        }
    }

    private void update(String id, float progess) {
        synchronized (mPaint) {
            mCircleMap.get(id).updateProperty(progess);
        }
    }

    public void start() {
        if (mRunning) {
            return;
        }
        mRunning = true;
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (mRunning) {
                    addCircle();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();
    }

    public void stop() {
        if (!mRunning) {
            return;
        }
        mRunning = false;
        synchronized (mPaint) {
            for (int i = 0; i < mAnimPool.size(); i++) {
                mAnimPool.get(i).mAnimator.cancel();
            }
            mCircleMap.clear();
            invalidate();
        }
    }

    private void addCircle() {
        synchronized (mPaint) {
            AnimatorHolder animatorHolder = obtainAnimatorHolder();
            CircleView circleView = createcircleView();
            mCircleMap.put(animatorHolder.mId, circleView);
            startAnim(animatorHolder.mAnimator);
        }
    }

    private void startAnim(final ValueAnimator animator) {
        post(new Runnable() {

            @Override
            public void run() {
                animator.start();
                postInvalidateDelayed(20);
            }
        });
    }

    private void removeCircle(String id) {
        synchronized (mPaint) {
            mCircleMap.remove(mCircleMap.get(id));
        }
    }

    private CircleView createcircleView() {
        int[] pos = randomXY();
        CircleView circleView = new CircleView();
        circleView.mX = pos[0];
        circleView.mY = pos[1];
        circleView.mCurX = pos[0];
        circleView.mCurY = pos[1];
        return circleView;
    }

    private AnimatorHolder obtainAnimatorHolder() {
        for (int i = 0; i < mAnimPool.size(); i++) {
            AnimatorHolder holder = mAnimPool.get(i);
            if (!holder.mAnimator.isRunning()) {
                return holder;
            }
        }
        AnimatorHolder holder = new AnimatorHolder();
        holder.mId = "holder" + holder.hashCode();
        holder.mAnimator = createAnim(holder.mId);
        mAnimPool.add(holder); // may be bug
        return holder;
    }

    private ValueAnimator createAnim(String id) {
        final String animId = id;
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(1000);

        animator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                LogUtils.log("azheng", "onAnimationEnd->mAnimId:" + animId);
                removeCircle(animId);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });

        animator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                update(animId, (Float) (animation.getAnimatedValue()));
            }
        });
        return animator;
    };

    private int[] randomXY() {
        int x = 0;
        int y = 0;
        double random = Math.random();
        if (random > 0.75) {
            x = (int) (0 - RADIUS);
            y = (int) (Math.random() * mHeight);
        } else if (random > 0.5) {
            y = (int) (0 - RADIUS);
            x = (int) (Math.random() * mWidth);
        } else if (random > 0.25) {
            x = (int) (mWidth + RADIUS);
            y = (int) (Math.random() * mHeight);
        } else {
            y = (int) (mHeight + RADIUS);
            x = (int) (Math.random() * mWidth);
        }
        return new int[] {x, y};
    }

    private class CircleView {
        private float mX;
        private float mY;
        private float mCurX;
        private float mCurY;
        private float mRadius = RADIUS;
        private int mAlpha = 255;

        private void updateProperty(float progess) {
            mCurX = mX + (mWidth / 2 - mX) * progess;
            mCurY = mY + (mHeight / 2 - mY) * progess;
            mRadius = RADIUS * (1 - progess);
            mAlpha = (int) (255 * (1 - progess));
        }
    }

    private class AnimatorHolder {
        private String mId;
        private ValueAnimator mAnimator;
    }
}