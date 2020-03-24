package pr.tongson.train_diy_view.pathmeasure;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * <b>Create Date:</b> 2020-01-02<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class GetSegmentView extends View {

    private Paint mPaint;
    private Path mDstPath;
    private Path mCirclePath;
    private PathMeasure mPathMeasure;
    private Float mCurAnimValue;

    public GetSegmentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //禁用硬件加速功能
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.BLACK);

        mDstPath = new Path();
        mCirclePath = new Path();
        mCirclePath.addCircle(100, 100, 50, Path.Direction.CW);
        mPathMeasure = new PathMeasure(mCirclePath, true);

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurAnimValue = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });

        animator.setDuration(20000);
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float length = mPathMeasure.getLength();
        float stop = length * mCurAnimValue;
        //        float start = (float) (stop - (0.5 - Math.abs(mCurAnimValue - 0.5)) * length);
        float start = 0;
        if (start >= 0.5) {
            start = 2 * mCurAnimValue - 1;
        }
        mDstPath.reset();
        //为了不影响展示效果
        canvas.drawColor(Color.WHITE);
        mPathMeasure.getSegment(start, stop, mDstPath, true);
        canvas.drawPath(mDstPath, mPaint);
    }
}
