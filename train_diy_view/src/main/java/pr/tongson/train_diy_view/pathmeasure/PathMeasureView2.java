package pr.tongson.train_diy_view.pathmeasure;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 * <b>Create Date:</b> 2020-01-02<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class PathMeasureView2 extends View {

    private Path mDst;
    private Path mPath;
    private Paint mPaint;
    private float mLength;
    private float mAnimValue;

    private PathMeasure mPathMeasure;


    public PathMeasureView2(Context context) {
        super(context);
    }

    public PathMeasureView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //设置Paint画笔基本属性
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mPath = new Path();
        mDst = new Path();

        mPath.addCircle(400, 400, 100, Path.Direction.CW);
        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mPath, true);

        mLength = mPathMeasure.getLength();

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDst.reset();
        mDst.lineTo(0, 0);

        float stop = mLength * mAnimValue;
        float start = 0;
        //float start = (float) (stop - ((0.5 - Math.abs(mAnimValue - 0.5)) * mLength));

        mPathMeasure.getSegment(0, stop, mDst, true);
        //mPathMeasure.getSegment(start, stop, mDst, true);
        canvas.drawPath(mDst, mPaint);
    }
}
