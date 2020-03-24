package pr.tongson.train_diy_view.pathmeasure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * <b>Create Date:</b> 2020-01-02<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class PathMeasureView extends View {


    public PathMeasureView(Context context) {
        super(context);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(50, 50);
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(0, 100);
        path.lineTo(100, 100);
        path.lineTo(100, 0);


        PathMeasure measure1 = new PathMeasure(path, false);
        PathMeasure measure2 = new PathMeasure(path, true);

        Log.d("TongsonLog", "forceClose=false--->" + measure1.getLength());
        Log.d("TongsonLog", "forceClose=true--->" + measure2.getLength());


        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawPath(path, paint);


        canvas.translate(100, 250);

        Path path1 = new Path();

        path1.addRect(-50, -50, 50, 50, Path.Direction.CW);
        canvas.drawPath(path1, paint);
        path1.addRect(-100, -100, 100, 100, Path.Direction.CW);
        canvas.drawPath(path1, paint);
        path1.addRect(-120, -120, 120, 120, Path.Direction.CW);
        canvas.drawPath(path1, paint);

        PathMeasure measure = new PathMeasure(path1, false);

        do {
            float len = measure.getLength();
            Log.d("TongsonLog", "len=" + len);
        } while (measure.nextContour());


        canvas.translate(0, 250);
        Path path2 = new Path();
        path2.addRect(-50, -50, 50, 50, Path.Direction.CW);
        canvas.drawPath(path2, paint);

        canvas.translate(0, 120);

        PathMeasure measure3 = new PathMeasure(path2, false);
        Path dst = new Path();
        dst.lineTo(10, 100);
        measure3.getSegment(0, 150, dst, false);
        canvas.drawPath(dst, paint);


    }
}
