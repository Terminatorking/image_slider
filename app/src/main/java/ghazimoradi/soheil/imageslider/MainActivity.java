package ghazimoradi.soheil.imageslider;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private PagerIndicator indicator;
    private ImagePagerAdapter adapter;
    private ArrayList<Integer> imageIds;

    private static final long SLIDE_DELAY_MS = 3000;
    private static final long SLIDE_PERIOD_MS = 3000;

    private Handler slideHandler;
    private Runnable slideRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = findViewById(R.id.pager);
        indicator = findViewById(R.id.imageIndicator);
        indicator.setViewPager(pager);

        imageIds = new ArrayList<>();
        for (int i = 1; i < 17; i++) {
            int id = getResources().getIdentifier("image" + i, "drawable", getPackageName());
            if (id != 0) {
                imageIds.add(id);
            }
        }

        if (!imageIds.isEmpty()) {
            if (indicator != null) {
                indicator.setIndicatorCount(imageIds.size());
            }
            adapter = new ImagePagerAdapter(imageIds);
            pager.setAdapter(adapter);

            slideHandler = new Handler(Looper.getMainLooper());
            setupAutoSlide();

        } else {
            pager.setVisibility(ViewPager.GONE);
            if (indicator != null) {
                indicator.setVisibility(ViewPager.GONE);
            }
        }
    }

    private void setupAutoSlide() {
        slideRunnable = new Runnable() {
            @Override
            public void run() {
                if (adapter == null || adapter.getCount() == 0) {
                    return;
                }
                int currentItem = pager.getCurrentItem();
                int nextItem = (currentItem + 1) % adapter.getCount();
                pager.setCurrentItem(nextItem, true);
                slideHandler.postDelayed(this, SLIDE_PERIOD_MS);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (slideHandler != null && slideRunnable != null && imageIds != null && !imageIds.isEmpty()) {
            slideHandler.postDelayed(slideRunnable, SLIDE_DELAY_MS);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (slideHandler != null && slideRunnable != null) {
            slideHandler.removeCallbacks(slideRunnable);
        }
    }

    @Override
    protected void onDestroy() {
        if (slideHandler != null && slideRunnable != null) {
            slideHandler.removeCallbacks(slideRunnable);
            slideHandler = null;
            slideRunnable = null;
        }
        super.onDestroy();
    }
}