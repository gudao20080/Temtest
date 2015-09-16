package wangkai.temtest;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Administrator on 2015/9/5.
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;
    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();
    }
}
