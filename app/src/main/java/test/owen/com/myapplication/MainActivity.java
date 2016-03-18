package test.owen.com.myapplication;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

import test.owen.com.myapplication.anim.AnimActivity;
import test.owen.com.myapplication.anim.AnimActivity2;
import test.owen.com.myapplication.anim.PathAnimActivity;
import test.owen.com.myapplication.emotion.EmotionActivity;
import test.owen.com.myapplication.search_word.SearchWordActivity;
import test.owen.com.myapplication.shape.ShapeActivity;
import test.owen.com.myapplication.shimmer.ShimmerActivity;
import test.owen.com.myapplication.sliding.SlidingAcitivity;
import test.owen.com.myapplication.surfaceview.SurfaceViewActivity;
import test.owen.com.myapplication.svg.SvgActivity;
import test.owen.com.myapplication.switch_gallery.SwitchGalleryActivity;
import test.owen.com.myapplication.textview.TextViewActivity;


public class MainActivity extends Activity {

    private ListView mListView;
    private ArrayList<ActivityTarget> mListData;

    private static class ActivityTarget {
        private String mTitle;
        private Class<?> mClass;

        private ActivityTarget(String title, Class<?> class1) {
            mTitle = title;
            mClass = class1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        LogUtils.log("azheng", "app memory->" + manager.getLargeMemoryClass());
        LogUtils.log("azheng", "onCreate->");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.log("azheng", "onStart->");
//        testArrayList();
    }

    ArrayList<Integer> mList = new ArrayList<>();
    private void testArrayList() {
        new Thread() {
            @Override
            public void run() {
                synchronized (MainActivity.class) {
                    for (int i = 10000; i < 20000; i++) {
                        mList.add(i);
                    }
                    Iterator<Integer> iterator = mList.iterator();
                    while (iterator.hasNext()) {
                        Integer num = iterator.next();
                        LogUtils.log("azheng", "Thread->" + num);
                    }
                    LogUtils.log("azheng", "Thread->size:" + mList.size());
                }
            }
        }.start();
        synchronized (MainActivity.class) {
            for (int i = 0; i < 1000000; i++) {
                mList.add(i);
            }
            Iterator<Integer> iterator = mList.iterator();
            while (iterator.hasNext()) {
                Integer num = iterator.next();
                iterator.remove();
                LogUtils.log("azheng", "" + num);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.log("azheng", "onResume->");
//        new Thread() {
//            @Override
//            public void run() {
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, ShapeActivity.class);
//                MainActivity.this.startActivity(intent);
//            }
//        }.start();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.log("azheng", "onRestart->");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.log("azheng", "onPause->");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.log("azheng", "onStop->");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.log("azheng", "onDestroy->");
    }

    private void initData() {
        mListData = new ArrayList<ActivityTarget>();

        mListData.add(new ActivityTarget("Shape", ShapeActivity.class));
        mListData.add(new ActivityTarget("SearchWord", SearchWordActivity.class));
        mListData.add(new ActivityTarget("Slide", SlidingAcitivity.class));
        mListData.add(new ActivityTarget("ImageSwitcher", SwitchGalleryActivity.class));
        mListData.add(new ActivityTarget("Anim", AnimActivity.class));
        mListData.add(new ActivityTarget("Anim2", AnimActivity2.class));
        mListData.add(new ActivityTarget("Emotion", EmotionActivity.class));
        mListData.add(new ActivityTarget("TextView", TextViewActivity.class));
        mListData.add(new ActivityTarget("Simmer", ShimmerActivity.class));
        mListData.add(new ActivityTarget("Surface", SurfaceViewActivity.class));
        mListData.add(new ActivityTarget("PathAnim", PathAnimActivity.class));
        mListData.add(new ActivityTarget("Svg", SvgActivity.class));
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.main_list);
        BaseAdapter adapter = new BaseAdapter() {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null;
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.main_list_item,
                            null);
                    holder.initView(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.setView(getItem(position));
                return convertView;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public String getItem(int position) {
                return mListData.get(position).mTitle;
            }

            @Override
            public int getCount() {
                return mListData.size();
            }
        };

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemClick(position);
            }
        });
    }

    protected void itemClick(int position) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, mListData.get(position).mClass);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static class ViewHolder {
        private TextView mItemName;

        private void initView(View view) {
            mItemName = (TextView) view.findViewById(R.id.item_name);
        }

        private void setView(String name) {
            mItemName.setText(name);
        }
    }
}
