package pr.tongson.train_slide.slide;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;

/**
 * <b>Create Date:</b> 2020-03-14<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class SlideView extends ViewGroup {


    private static final String TAG = "Tongson DIYView";

    public SlideView(Context context) {
        super(context);
        Log.i(TAG, "SlideView(Context context)");
        init();
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "SlideView(Context context, AttributeSet attrs)");
        init();
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i(TAG, "SlideView(Context context, AttributeSet attrs, int defStyleAttr)");
        init();
    }

    private void init() {
        Log.i(TAG, "init");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i(TAG, "onLayout,changed:" + changed + ",l:" + l + ",t:" + t + ",r:" + r + ",b:" + b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw");
        canvas.drawColor(Color.RED);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);

    }

    int lastX, lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent");

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
                break;
        }


        return true;
    }
}
