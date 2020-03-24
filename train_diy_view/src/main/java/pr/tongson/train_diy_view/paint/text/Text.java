package pr.tongson.train_diy_view.paint.text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * <b>Create Date:</b> 2020-01-02<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class Text extends View {
    public Text(Context context, @androidx.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int baseLineX = 0;
        int baseLineY = 200;

        //画基线
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(120);
        paint.setStrokeWidth(3);



        //画四格线
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();

        Log.i("TongsonLog", "baseLineY=" + baseLineY + ",ascent=" + fontMetrics.ascent + ",descent=" + fontMetrics.descent + ",top=" + fontMetrics.top + ",bottom=" + fontMetrics.bottom);

        float top = baseLineY + fontMetrics.top;
        float ascent = baseLineY + fontMetrics.ascent;
        float descent = baseLineY + fontMetrics.descent;
        float bottom = baseLineY + fontMetrics.bottom;

        paint.setColor(Color.YELLOW);
        canvas.drawLine(baseLineX, top, 3000, top, paint);

        paint.setColor(Color.BLUE);
        canvas.drawLine(baseLineX, ascent, 3000, ascent, paint);

        paint.setColor(Color.BLACK);
        canvas.drawLine(baseLineX, baseLineY, 3000, baseLineY, paint);

        paint.setColor(Color.GREEN);
        canvas.drawLine(baseLineX, descent, 3000, descent, paint);

        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, bottom, 3000, bottom, paint);

        //写文字
        paint.setColor(Color.GREEN);
        canvas.drawText("Harvic\'s blog", baseLineX, baseLineY, paint);

    }
}
