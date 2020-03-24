package pr.tongson.train_scall_a;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * <b>Create Date:</b> 2020-03-17<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class MyView extends AppCompatImageView {
    private static final String TAG = "Tongson MyView";

    int height;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    ValueAnimator mValueAnimator;
    //    mValueAnimator= ObjectAnimator.ofFloat(downChildView1Top,)


    int width;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        Log.i(TAG, "width:" + width);
        super.onMeasure(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect(0, 0, width, 200);
        RectF rectf = new RectF(0, 0, width, 200);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.iu);
        canvas.drawBitmap(bitmap, rect, rectf, null);
    }
}
