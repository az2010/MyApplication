package test.owen.com.myapplication.switch_gallery;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

import test.owen.com.myapplication.R;

public class SwitchGalleryActivity extends Activity implements ViewFactory {

    private Gallery mGallery;
    private ImageSwitcher mSwitcher;

    private int[] mImages = {R.mipmap.one, R.mipmap.two, R.mipmap.three, R.mipmap.four,
            R.mipmap.five};
    private int[] mThumbs = {R.mipmap.one2, R.mipmap.two2, R.mipmap.three2, R.mipmap.four2,
            R.mipmap.five2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.switch_gallery);
        initData();
        initView();
    }

    private void initData() {
    }

    private void initView() {
        mGallery = (Gallery) findViewById(R.id.gallery);
        mSwitcher = (ImageSwitcher) findViewById(R.id.switcher);

        mSwitcher.setFactory(this);
        mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        mGallery.setAdapter(new GalleryAdapter());
        mGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSwitcher.setImageResource(mImages[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    @Override
    public View makeView() {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundColor(0xFF000000);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ImageSwitcher.LayoutParams.MATCH_PARENT,
                ImageSwitcher.LayoutParams.MATCH_PARENT));
        return imageView;
    }

    private class GalleryAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mThumbs.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                convertView = LayoutInflater.from(SwitchGalleryActivity.this).inflate(
                        R.layout.switch_gallery_item, null);
            }
            imageView = (ImageView) convertView.findViewById(R.id.gallery_view);
            imageView.setImageResource(mThumbs[position]);
            return convertView;
        }

    }

}
