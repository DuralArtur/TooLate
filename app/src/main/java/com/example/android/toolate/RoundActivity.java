package com.example.android.toolate;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RoundActivity extends AppCompatActivity {

    @BindView(R.id.countdownTV)
    TextView countdownTV;
    @OnClick(R.id.tick_fab)
    public void guessedIt(){
        curPos = mPager.getCurrentItem() % words.size();
        Toast.makeText(getApplicationContext(),""+curPos, Toast.LENGTH_SHORT).show();
        words.remove(curPos);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        if (curPos<1){
            mPager.setCurrentItem(0);
        } else {
            mPager.setCurrentItem(curPos-1);
        }
    }
    private int curPos;
    private List<String> words = new ArrayList<>();
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
        new CountDownTimer(60000, 500) {

            public void onTick(long millisUntilFinished) {
                countdownTV.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {

            }
        }.start();
        words.add("zero");
        words.add("jeden");
        words.add("dwa");
        words.add("trzy");
        words.add("cztery");
        words.add("piec");
        words.add("szesc");
        words.add("siedem");
        words.add("osiem");
        words.add("dziewiec");
        mPager = ButterKnife.findById(this, R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setClipChildren(false);
        mPager.setClipToPadding(false);
        mPager.setPageMargin(-192);
        mPager.setOffscreenPageLimit(3);
        mPager.setPadding(96, 0, 96, 0);
        mPager.setOffscreenPageLimit(3);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

//        @Override
//        public int getItemPosition(Object object) {
//            return words.size() * NUM_MULT/2;
//        }

        @Override
        public Fragment getItem(int position) {
            WordSlideFragment wordSlideFragment = WordSlideFragment.newInstance(words.get(position));
            return wordSlideFragment;
        }

//        @Override
//        public float getPageWidth(int position) {
//            return 0.93f;
//        }

        @Override
        public int getCount() {
            return words.size();
        }
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.73f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

}


