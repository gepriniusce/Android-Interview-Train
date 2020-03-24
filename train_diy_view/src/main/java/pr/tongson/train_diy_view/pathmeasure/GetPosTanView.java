package pr.tongson.train_diy_view.pathmeasure;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import pr.tongson.train_diy_view.R;


/**
 * <b>Create Date:</b> 2020-01-02<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class GetPosTanView extends View {

    private Path mCirclePath, mDstPath;
    private Paint mPaint;
    private Float mCurAnimValue;
    private Bitmap mArrawBmp;
    private PathMeasure mPathMeasure;


    public GetPosTanView(Context context, @androidx.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mArrawBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ziwei_arrow_purple);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.BLACK);

        mDstPath = new Path();
        mCirclePath = new Path();
        mCirclePath.addCircle(100, 100, 50, Path.Direction.CW);
        mPathMeasure = new PathMeasure(mCirclePath, true);

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setRepeatCount(-1);
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

    private float[] pos = new float[2];
    private float[] tan = new float[2];

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

        //旋转箭头图片，并绘制

        //矩阵
        Matrix matrix = new Matrix();

        ////把获取到的位置信息和切边信息分别保存在pos和tan数组中；
        //        mPathMeasure.getPosTan(start, pos, tan);
        //        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI);
        //        matrix.postRotate(degrees, mArrawBmp.getWidth() / 2, mArrawBmp.getHeight() / 2);
        //        matrix.postTranslate(pos[0], pos[1]);
        //        matrix.postRotate(degrees, mArrawBmp.getWidth() / 2, mArrawBmp.getHeight() / 2);
        //        matrix.postTranslate(pos[0]-mArrawBmp.getWidth()/2,pos[1]-mArrawBmp.getHeight()/2);

        //只是PathMeasure.getPosTan()函数的另一种实现而已，POSITION_MATRIX_FLAG是位置信息，TANGENT_MATRIX_FLAG是切边信息。
        mPathMeasure.getMatrix(stop, matrix, PathMeasure.POSITION_MATRIX_FLAG | PathMeasure.TANGENT_MATRIX_FLAG);
        //移动半个图片大小。
        matrix.preTranslate(-mArrawBmp.getWidth() / 2, -mArrawBmp.getHeight() / 2);

        canvas.drawBitmap(mArrawBmp, matrix, mPaint);


    }
}
