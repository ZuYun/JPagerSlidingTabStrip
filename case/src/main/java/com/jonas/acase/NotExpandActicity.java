package com.jonas.acase;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.jonas.acase.CustomTabStyle.CustomTabStyle;
import com.jonas.acase.eventbusmsg.PromptMsg;
import com.jonas.acase.fragment.DemoCardFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.security.SecureRandom;

import april.yun.ISlidingTabStrip;
import april.yun.other.JTabStyleDelegate;

import static april.yun.other.JTabStyleBuilder.STYLE_DEFAULT;
import static april.yun.other.JTabStyleBuilder.STYLE_DOTS;
import static april.yun.other.JTabStyleBuilder.STYLE_ROUND;
import static april.yun.tabstyle.JTabStyle.MOVESTYLE_DEFAULT;

public class NotExpandActicity extends FragmentActivity {
    private ISlidingTabStrip tabs_up;
    private ISlidingTabStrip tabs_up1;
    private ISlidingTabStrip tabs_up2;
    private ISlidingTabStrip tabs_up3;
    private ISlidingTabStrip dots;
    private ISlidingTabStrip tabs_buttom;
    private ViewPager pager;
    private MyPagerAdapter adapter;
    private String[] mTitles;


    @Override protected void onStart() {
        super.onStart();
    }


    @Override protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public void setPromptMsg(ISlidingTabStrip slidingTabStrip) {
        for (int i = 0; i < pager.getAdapter().getCount(); i++) {
            slidingTabStrip.setPromptNum(i, -3 + new SecureRandom().nextInt(10));
        }
    }


    public void clearPromptMsg(ISlidingTabStrip slidingTabStrip) {
        for (int i = 0; i < pager.getAdapter().getCount(); i++) {
            slidingTabStrip.setPromptNum(i, 0);
        }
    }


    public void showPromptMsg(ISlidingTabStrip slidingTabStrip) {
        slidingTabStrip.setPromptNum(1, 9).setPromptNum(0, 10).setPromptNum(2, -9).setPromptNum(3, 100);
    }


    @Subscribe public void randomMsg(PromptMsg msg) {
        if (msg.mType == PromptMsg.RANDOM) {
            setPromptMsg(tabs_up);
            setPromptMsg(tabs_up1);
            setPromptMsg(tabs_up2);
            setPromptMsg(tabs_up3);
            setPromptMsg(tabs_buttom);
        }
    }


    @Subscribe public void showMsg(PromptMsg msg) {
        if (msg.mType == PromptMsg.SHOW) {
            showPromptMsg(tabs_up);
            showPromptMsg(tabs_up1);
            showPromptMsg(tabs_up2);
            showPromptMsg(tabs_up3);
            tabs_buttom.getTabStyleDelegate().setPromptBgColor(Color.TRANSPARENT);
            showPromptMsg(tabs_buttom);
        }
    }


    @Subscribe public void clearMsg(PromptMsg msg) {
        if (msg.mType == PromptMsg.CLEAR) {
            clearPromptMsg(tabs_up);
            clearPromptMsg(tabs_up1);
            clearPromptMsg(tabs_up2);
            clearPromptMsg(tabs_up3);
            clearPromptMsg(tabs_buttom);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_expand_acticity);
        setActionBar(((Toolbar) findViewById(R.id.toolbar)));
        EventBus.getDefault().register(this);
        tabs_up = (ISlidingTabStrip) findViewById(R.id.tabs);
        tabs_up1 = (ISlidingTabStrip) findViewById(R.id.tabs_1);
        tabs_up2 = (ISlidingTabStrip) findViewById(R.id.tabs_2);
        tabs_up3 = (ISlidingTabStrip) findViewById(R.id.tabs_3);

        tabs_buttom = (ISlidingTabStrip) findViewById(R.id.tab_buttom);
        dots = (ISlidingTabStrip) findViewById(R.id.dots);

        setupTabStrips();

        setupViewpager();
    }


    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.sts_badge:
                startActivity(new Intent(this, NotExpandActicity.class));
                return true;
            default:
                startActivity(new Intent(this, PromptActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupTabStrips() {

        setupStrip(tabs_up.getTabStyleDelegate(), STYLE_ROUND);
        setupStrip(tabs_up1.getTabStyleDelegate(), STYLE_DEFAULT);
        setupStrip(tabs_up2.getTabStyleDelegate(), STYLE_DEFAULT);
        setupStrip(tabs_up3.getTabStyleDelegate(), STYLE_DEFAULT);
        setupStrip(tabs_buttom.getTabStyleDelegate(), STYLE_DEFAULT);
        setupStrip(dots.getTabStyleDelegate(), STYLE_DOTS);
        tabs_buttom.getTabStyleDelegate()
                   .setIndicatorHeight(0)
                   .setDividerColor(Color.TRANSPARENT);
        tabs_up.getTabStyleDelegate();
        tabs_up1.getTabStyleDelegate()
                .setDividerColor(Color.TRANSPARENT)
                .setBackgroundColor(Color.parseColor("#6B8DB3"))
                .setIndicatorColor(Color.parseColor("#4B6A8A"))
                .setTextColor(Color.WHITE,Color.WHITE).setFrameColor(Color.TRANSPARENT);

        tabs_up3.getTabStyleDelegate()
                .setDividerColor(Color.TRANSPARENT)
                .setIndicatorColor(Color.WHITE)
                .setTextColor(Color.WHITE,Color.GRAY)
                .setBackgroundColor(Color.parseColor("#57365C"))
                .setFrameColor(Color.TRANSPARENT).setDividerPadding(20).setIndicatorHeight(5)
                .getJTabStyle().moveStyle = MOVESTYLE_DEFAULT;

        tabs_up2.getTabStyleDelegate()
                .setJTabStyle(new CustomTabStyle(tabs_up2))
                .setFrameColor(Color.TRANSPARENT)
                .setTextColor(Color.parseColor("#57365D"),Color.GRAY)
                .setIndicatorColor(Color.parseColor("#57365D"))
                .setDividerPadding(8)
                .setDividerColor(Color.parseColor("#57365D"));
    }


    private void setupStrip(JTabStyleDelegate tabStyleDelegate, int type) {
        tabStyleDelegate.setJTabStyle(type)
                        .setTabPadding(20)
                        //.setShouldExpand(true)
                        .setFrameColor(Color.parseColor("#45C01A"))
                        .setTabTextSize(getDimen(R.dimen.tabstrip_textsize))
                        //        .setTextColor(Color.parseColor("#FB6522"))
                        .setTextColor(R.drawable.tabstripbg)
                        .setDividerColor(Color.parseColor("#45C01A"))
                        .setDividerPadding(0)
                        .setUnderlineColor(Color.parseColor("#3045C01A"))
                        .setUnderlineHeight(0)
                        .setIndicatorColor(Color.parseColor("#7045C01A"))
                        .setIndicatorHeight(getDimen(R.dimen.sug_event_tabheight)).setScrollOffset(200);
    }


    private void setupViewpager() {
        mTitles = getResources().getStringArray(R.array.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
                getResources().getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs_up.bindViewPager(pager);
        tabs_up1.bindViewPager(pager);
        tabs_up2.bindViewPager(pager);
        //tabs_up2.setPromptNum(1, 9).setPromptNum(0, 10).setPromptNum(2, -9).setPromptNum(3, 100);
        tabs_up3.bindViewPager(pager);
        //tabs_up3.setPromptNum(2, 18);
        tabs_buttom.bindViewPager(pager);
        //tabs_buttom.setPromptNum(1, 9).setPromptNum(0, 10).setPromptNum(2, -9).setPromptNum(3, 100);
        //tabs_up1.setPromptNum(1, 9).setPromptNum(0, 10).setPromptNum(2, -9).setPromptNum(3, 100);
        //tabs_up.setPromptNum(1, 9).setPromptNum(0, 10).setPromptNum(2, -9).setPromptNum(3, 100);
        //tabs_up3.setPromptNum(1, 9).setPromptNum(0, 10).setPromptNum(2, -9).setPromptNum(3, 100);

        dots.bindViewPager(pager);
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override public CharSequence getPageTitle(int position) {
            return mTitles[position % 4];
        }


        @Override public int getCount() {
            return mTitles.length*2;
            //return mTitles.length;
            //return 1;
        }


        @Override public Fragment getItem(int position) {
            return DemoCardFragment.newInstance(position);
        }

    }


    private int getDimen(int dimen) {
        return (int) getResources().getDimension(dimen);
    }
}
