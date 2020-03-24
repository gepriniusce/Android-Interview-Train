package pr.tongson.train_scall_a;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import pr.tongson.train_scall_a.ui.UIUtils;
import pr.tongson.train_scall_a.ui.main.BottomFragment;

/**
 * <b>Create Date:</b> 2020-03-17<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class MyViewGroup extends ViewGroup {

    private static final String TAG = "Tongson MyViewGroup";


    public MyViewGroup(Context context) {
        super(context);
        init();
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    ObjectAnimator mObjectAnimator;

    private void init() {
        Log.i(TAG, "init");
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.i(TAG, "onFinishInflate,getWidth():" + getWidth() + "getHeight()" + getHeight());
        if (childView0 == null) {
            childView0 = getChildAt(0);
        }
        if (childView1 == null) {
            childView1 = getChildAt(1);
        }

        if (getContext() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getContext();
            activity.getSupportFragmentManager().getFragments().get(0).getChildFragmentManager().beginTransaction().replace(childView1.getId(), BottomFragment.newInstance()).commitNow();
        }


    }

    boolean flag;


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /**
         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
         */
        //        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        //        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        //        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        //        Log.i(TAG, "widthMode:" + widthMode);
        //        Log.i(TAG, "heightMode:" + heightMode);
        //        Log.i(TAG, "sizeWidth:" + sizeWidth);
        //        Log.i(TAG, "sizeHeight:" + sizeHeight);

        //MeasureSpec.AT_MOST->父容器指定一个可用大小，View的大小不能超过这个值，LayoutParams.wrap_content->最大模式，最大为窗口大小
        //MeasureSpec.EXACTLY->父容器检测出View的大小，View的大小就是SpecSize LayoutParams.match_parent->精确模式，窗口大小
        //MeasureSpec.UNSPECIFIED->父容器不对View做任何限制，系统内部使用->精确模式，大小为LayoutParams的大小


        if (!flag) {
            flag = true;
            float displayMetricsWidth = UIUtils.getInstance(getContext()).getDisplayMetricsWidth();
            LayoutParams layoutParams = childView0.getLayoutParams();
            //等比缩放
            float s = ((float) layoutParams.height / (float) layoutParams.width);
            float layoutHeight = (s * displayMetricsWidth);
            layoutParams.width = (int) (displayMetricsWidth);
            layoutParams.height = (int) layoutHeight;


        }

        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);


        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            Log.i(TAG, "onMeasure-->i:" + i + "width:" + childView.getMeasuredWidth() + "height:" + childView.getMeasuredHeight());
        }


        //        int cCount = getChildCount();
        //
        //        int cWidth = 0;
        //        int cHeight = 0;

        //        View childView0 = getChildAt(0);
        //        if (childView0 instanceof ImageView) {
        //            ImageView imageView = (ImageView) childView0;
        //            cWidth = imageView.getMeasuredWidth();
        //            cHeight = imageView.getMeasuredHeight();
        //            measureChild(imageView, cWidth, cHeight/3);
        //        }
        //        childView1 = getChildAt(1);
        //        measureChild(childView1, sizeWidth, sizeHeight);


        //        View childView;
        //        for (int i = 0; i < cCount; i++) {
        //            childView = getChildAt(i);
        //            cWidth = childView.getMeasuredWidth();
        //            cHeight = childView.getMeasuredHeight();
        //            if (cCount == 1) {
        //                cHeight = sizeHeight;
        //            }
        //            setMeasuredDimension(cWidth, cHeight);
        //        }

        // 测量并保存layout的宽高
        //setMeasuredDimension(sizeWidth, sizeHeight);
    }

    View childView0, childView1, divider;


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            Log.i(TAG, "onLayout-->i:" + i + "width:" + childView.getMeasuredWidth() + "height:" + childView.getMeasuredHeight());
        }

        View childView0 = getChildAt(0);
        if (childView0 instanceof ImageView) {
            ImageView imageView = (ImageView) childView0;
            imageView.layout(0, 0, imageView.getMeasuredWidth(), imageView.getMeasuredHeight());
            divierHeight = imageView.getMeasuredHeight();
        }

        if (childView1 == null) {
            childView1 = getChildAt(1);
        }
        childView1.layout(0, divierHeight, childView1.getMeasuredWidth(), divierHeight + childView1.getMeasuredHeight());


        if (childView1 != null && childView1 instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) childView1;

            ViewGroup viewGroup2 = (ViewGroup) viewGroup.getChildAt(0);

            Log.i(TAG, "viewGroup2:" + viewGroup2.getChildCount());
            divider = viewGroup2.findViewById(R.id.divider);
            if (divider != null) {
                divider.setAlpha(0f);
            }
        }
    }


    int offset = 2;
    float topFlag = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        //        Log.i(TAG, "onInterceptTouchEvent");
        int x = (int) event.getX();
        int y = (int) event.getY();

        //        Log.i(TAG, "childView1.gettop:" + childView1.getTop());
        //        Log.i(TAG, "childView1.getX:" + childView1.getY());

        boolean isInterceptBottom = false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //lastX = x;
                lastY = y;
                downChildView1Top = childView1.getTop();
                downChildView1Bottom = childView1.getBottom();
                break;
            case MotionEvent.ACTION_MOVE:
                //int offsetX = x - lastX;
                int offsetY = y - lastY;
                int moveChildView1Top = childView1.getTop();

                if (moveChildView1Top > divierHeight) {
                    return super.onInterceptTouchEvent(event);
                }
                //滑动状态
                if (moveChildView1Top < divierHeight) {
                    //这里要处理RecyclerView传过来的拦截/*&& offsetY < 0*/
                    if (moveChildView1Top == topFlag && offsetY < 0) {
                        //不拦截把任务交给子view
                        return super.onInterceptTouchEvent(event);
                    }
                    //拦截交给自己的onTouchEvent处理
                    isInterceptBottom = true;
                    break;
                }
                if (moveChildView1Top == divierHeight) {
                    if (offsetY > 0) {
                        //表示下滑，不拦截把任务交给子view
                        return super.onInterceptTouchEvent(event);
                    }
                }
                //拦截交给自己的onTouchEvent处理
                isInterceptBottom = true;
                break;
            default:
                break;

        }

        return isInterceptBottom;
    }

    int lastX, lastY;
    int downChildView1Top, downChildView1Bottom;
    private int divierHeight = 500, limit = 50;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //        Log.i(TAG, "onTouchEvent");
        int x = (int) event.getX();
        int y = (int) event.getY();
        boolean isTouchBottom = false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //lastX = x;
                lastY = y;
                downChildView1Top = childView1.getTop();
                downChildView1Bottom = childView1.getBottom();
                break;
            case MotionEvent.ACTION_MOVE:
                //int offsetX = x - lastX;
                int offsetY = y - lastY;
                int moveChildView1Top = childView1.getTop();
                if (moveChildView1Top > divierHeight) {
                    return super.onTouchEvent(event);
                }
                //滑动状态
                if (moveChildView1Top < divierHeight) {
                    if (moveChildView1Top == topFlag && offsetY < 0) {
                        //不拦截把任务交给子view
                        return super.onTouchEvent(event);
                    }/*
                    //拦截交给自己的onTouchEvent处理
                    isTouchBottom = true;
                    break;*/
                }
                if (moveChildView1Top == divierHeight) {
                    if (offsetY > 0) {
                        //表示下滑，不拦截把任务交给子view
                        return super.onTouchEvent(event);
                    }
                }
                //自己的onTouchEvent处理
                if (moveChildView1Top < 0) {
                    return false;
                }

                int t = downChildView1Top + offsetY;
                int b = downChildView1Bottom + offsetY;

                float alpha = (float) moveChildView1Top / (float) divierHeight;

                //吸附效果
                if (t > divierHeight - limit) {
                    t = divierHeight;
                    alpha = 1f;
                }
                if (t < limit) {
                    t = 0;
                }
                if (moveChildView1Top != 0) {
                    childView0.setAlpha(alpha);
                    if (divider != null) {
                        //&& alpha < 0.5f
                        divider.setAlpha(1 - alpha);
                    }
                }

                childView1.layout(getLeft(), t, getRight(), b);

                isTouchBottom = true;
                break;
            default:
                break;
        }

        return isTouchBottom;
    }


}
