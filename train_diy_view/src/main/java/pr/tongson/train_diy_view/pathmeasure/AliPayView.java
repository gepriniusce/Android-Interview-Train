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

/**
 * <b>Create Date:</b> 2020-01-02<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class AliPayView extends View {


    private Path mDstPath;
    private Path mCirclePath;
    private Paint mPaint;
    private int mCentX, mCentY, mRadius;
    private PathMeasure mPathMeasure;
    private Float mCurAnimValue;

    public AliPayView(Context context, @androidx.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(8);
        mDstPath = new Path();
        mCirclePath = new Path();

        mCentX = 100;
        mCentY = 100;
        mRadius = 50;
        mCirclePath.addCircle(mCentX, mCentY, mRadius, Path.Direction.CW);
        mCirclePath.moveTo(mCentX - mRadius / 2, mCentY);
        mCirclePath.lineTo(mCentX, mCentY + mRadius / 2);
        mCirclePath.lineTo(mCentX + mRadius / 2, mCentY - mRadius / 3);
        mPathMeasure = new PathMeasure(mCirclePath, false);

        ValueAnimator animator = ValueAnimator.ofFloat(0, 2);
        animator.setRepeatCount(-1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurAnimValue = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(5000);
        animator.start();
    }

    boolean mNext = false;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        if (mCurAnimValue < 1) {
            float stop = mPathMeasure.getLength() * mCurAnimValue;
            mPathMeasure.getSegment(0, stop, mDstPath, true);
        } else {
            if (!mNext) {
                mNext = true;
                mPathMeasure.getSegment(0, mPathMeasure.getLength(), mDstPath, true);
                mPathMeasure.nextContour();
            }
            float stop = mPathMeasure.getLength() * (mCurAnimValue - 1);
            mPathMeasure.getSegment(0, stop, mDstPath, true);
        }
        canvas.drawPath(mDstPath, mPaint);
    }
}
