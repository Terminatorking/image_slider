package ghazimoradi.soheil.imageslider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.ViewPager;

public class PagerIndicator extends AppCompatImageView {
    private static final int CIRCLE_RADIUS = 20;
    private static final int CIRCLE_SPACE = 20;
    private static final int VERTICAL_PADDING = 10;
    private static final int CIRCLE_STROKE_COLOR = Color.GRAY;
    private static final int CIRCLE_FILL_COLOR = Color.LTGRAY;

    private Paint fillPaint;
    private Paint strokePaint;
    private int count;
    private int currentPageIndex;

    public void setViewPager(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        setCurrentPage(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                }
        );
    }

    public void setCurrentPage(int currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
        invalidate();
    }

    public void setIndicatorCount(int count) {
        this.count = count;
        requestLayout(); //Tell the system the view size might have changed
        invalidate();
    }

    public PagerIndicator(Context context) {
        super(context);
        initialize();
    }

    public PagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public PagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(CIRCLE_FILL_COLOR);
        fillPaint.setAntiAlias(true);

        strokePaint = new Paint();
        strokePaint.setStrokeWidth(3f);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(CIRCLE_STROKE_COLOR);
        strokePaint.setAntiAlias(true);
    }

    private int calculateDesiredHeight() {
        return (CIRCLE_RADIUS * 2) + (VERTICAL_PADDING * 2) + getPaddingTop() + getPaddingBottom();
    }

    private int calculateDesiredWidth() {
        if (count <= 0) {
            return getPaddingLeft() + getPaddingRight();
        }

        int totalCirclesWidth = count * (CIRCLE_RADIUS * 2);
        int totalSpacesWidth = (count > 1) ? (count - 1) * CIRCLE_SPACE : 0;
        return totalCirclesWidth + totalSpacesWidth + getPaddingLeft() + getPaddingRight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = calculateDesiredWidth();
        int desiredHeight = calculateDesiredHeight();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int finalWidth;
        int finalHeight;

        if (widthMode == MeasureSpec.EXACTLY) {
            finalWidth = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            finalWidth = Math.min(desiredWidth, widthSize);
        } else {
            finalWidth = desiredWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            finalHeight = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            finalHeight = Math.min(desiredHeight, heightSize);
        } else {
            finalHeight = desiredHeight;
        }
        setMeasuredDimension(finalWidth, finalHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (count <= 0) {
            return;
        }

        int currentIndicatorWidth;
        int totalCirclesWidth = count * (CIRCLE_RADIUS * 2);
        int totalSpacesWidth = (count > 1) ? (count - 1) * CIRCLE_SPACE : 0;

        currentIndicatorWidth = totalCirclesWidth + totalSpacesWidth;
        float startX = (float) (getWidth() - currentIndicatorWidth) / 2;
        float currentX = startX + CIRCLE_RADIUS;
        float centerY = (float) getHeight() / 2;

        for (int i = 0; i < count; i++) {
            Paint paint = strokePaint;
            if (i == currentPageIndex) {
                paint = fillPaint;
            }
            canvas.drawCircle(currentX, centerY, CIRCLE_RADIUS, paint);
            currentX += (CIRCLE_RADIUS * 2) + CIRCLE_SPACE;
        }
    }
}