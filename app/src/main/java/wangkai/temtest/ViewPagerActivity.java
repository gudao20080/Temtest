package wangkai.temtest;

import android.app.Fragment;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ViewPagerActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    @Bind(R.id.viewPager)
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        VPFragment vp1 = VPFragment.newInstance("第一个页面", "");
        VPFragment vp2 = VPFragment.newInstance("第二个页面", "");
        VPFragment vp3 = VPFragment.newInstance("第三个页面", "");
        VPFragment vp4 = VPFragment.newInstance("第四个页面", "");
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(vp1);
        fragments.add(vp2);
        fragments.add(vp3);
        fragments.add(vp4);

        TransPagerAdapter pagerAdapter = new TransPagerAdapter(getFragmentManager(), fragments);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.d(TAG, "onPageScrolled() returned: " + "position: " + position  + " positionOffset: " + positionOffset
//                        + " positionOffsetPixels: " + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {

                Log.d(TAG, "page.getId(): " + page.getId() + " Position: " + position);
                page.setAlpha(1 - Math.abs(position));
            }
        });
        Rect rect = new Rect();
        mViewPager.getGlobalVisibleRect(rect, new Point(0, 0));
    }


}
