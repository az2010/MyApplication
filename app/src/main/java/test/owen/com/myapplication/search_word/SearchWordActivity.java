package test.owen.com.myapplication.search_word;

import java.util.ArrayList;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import test.owen.com.myapplication.LogUtils;
import test.owen.com.myapplication.R;

public class SearchWordActivity extends Activity {

    private int mMarginTop;
    private int mMarginHorizontal;
    private int mPadding;
    private int mTextSize;

    private LinearLayout mParent;
    private ArrayList<String> mWordList;
    private ArrayList<Integer> mColors;
    private SparseArray<ObjectAnimator> mAnim;

    private OnClickListener mClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            int position = (Integer) v.getTag();
            Toast.makeText(SearchWordActivity.this, mWordList.get(position), Toast.LENGTH_SHORT).show();
            ObjectAnimator animator = mAnim.get(position);
            if (animator != null && !animator.isRunning()) {
                animator.start();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParentLayout();
        setContentView(mParent);
        initData();
        initChild();
        initAnimation();
    }

    private int mCount = 0;

    private void initAnimation() {
        ValueAnimator animator = ObjectAnimator.ofFloat(0f, 2f);
        animator.setDuration(2000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCount++;
                float value = (Float) animation.getAnimatedValue();
                LogUtils.log("azheng", "onAnimation value->" + value);
                if (value == 2) {
                    LogUtils.log("azheng", "onAnimation count->" + mCount);
                }
            }
        });
        animator.start();
    }

    private void initParentLayout() {
        mParent = new LinearLayout(this);
        mParent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mParent.setOrientation(LinearLayout.VERTICAL);
        mParent.setBackgroundColor(getResources().getColor(android.R.color.white));
    }

    private void initData() {
        Resources res = getResources();
        mMarginTop = res.getDimensionPixelSize(R.dimen.search_word_margin_top);
        mMarginHorizontal = res.getDimensionPixelSize(R.dimen.search_word_margin_horizontal);
        mPadding = res.getDimensionPixelSize(R.dimen.search_word_padding);
        mTextSize = res.getDimensionPixelSize(R.dimen.search_word_size);

        mColors = new ArrayList<Integer>();
        mColors.add(Integer.valueOf(res.getColor(android.R.color.darker_gray)));
        mColors.add(Integer.valueOf(res.getColor(android.R.color.holo_blue_dark)));
        mColors.add(Integer.valueOf(res.getColor(android.R.color.holo_green_dark)));
        mColors.add(Integer.valueOf(res.getColor(android.R.color.holo_orange_dark)));

        mWordList = new ArrayList<String>();
        mWordList.add("百将行");
        mWordList.add("苍穹变");
        mWordList.add("梦幻神域");
        mWordList.add("愤怒的小鸟2");
        mWordList.add("西游降魔篇");
        mWordList.add("梦幻西游");
        mWordList.add("盗墓");
        mWordList.add("花千骨");
        mWordList.add("不良人");
        mWordList.add("全民奇迹");
        mWordList.add("游戏大厅");
        mWordList.add("杀人游戏");
    }

    private void initChild() {
        LinearLayout layout = createChildLayout();
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int curWidth = 0;
        for (int i = 0; i < mWordList.size(); i++) {
            String text = mWordList.get(i);
            TextView child = createChildView(i, text);
            float textWidth = child.getPaint().measureText(text) + (mMarginHorizontal + mPadding) * 2;
            curWidth += textWidth;
            if (curWidth > width) {
                layout.setGravity(Gravity.CENTER_HORIZONTAL);
                mParent.addView(layout);
                layout = createChildLayout();
                curWidth = (int) textWidth;
            }
            if (curWidth >= width / 2) {
                layout.setGravity(Gravity.CENTER_HORIZONTAL);
            }
            layout.addView(child);
        }
        mParent.addView(layout);
    }

    private LinearLayout createChildLayout() {
        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(params);
        return layout;
    }

    private TextView createChildView(int index, String text) {
        TextView child = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = mMarginHorizontal;
        params.rightMargin = mMarginHorizontal;
        params.topMargin = mMarginTop;
        child.setLayoutParams(params);
        child.setPadding(mPadding, mPadding, mPadding, mPadding);
        child.setBackgroundColor(getTextColor());
        child.setTextColor(getResources().getColor(android.R.color.white));
        child.setTextSize(mTextSize);
        child.setText(text);
        child.setOnClickListener(mClickListener);
        child.setTag(index);
        addAlphaAnim(index, child);
        return child;
    }

    private int getTextColor() {
        int random = (int) (Math.random() * mColors.size());
        return mColors.get(random);
    }

    private void addAlphaAnim(int key, Object target) {
        if (mAnim == null) {
            mAnim = new SparseArray<ObjectAnimator>();
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotationX", 0f, 90f, 0f);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        mAnim.append(key, animator);
    }
}
