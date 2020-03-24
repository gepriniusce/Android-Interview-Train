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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.customview.widget.ViewDragHelper;

/**
 * <b>Create Date:</b> 2020-03-14<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class DragView extends ViewGroup {
    private static final String TAG = "Tongson DIYView";

    /**
     * https://blog.csdn.net/qq_22393017/article/details/78197442
     */
    private ViewDragHelper mViewDragHelper;

    public DragView(Context context) {
        super(context);
        Log.i(TAG, "DragView(Context context)");

        init();
    }

    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "DragView(Context context, AttributeSet attrs)");
        init();
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i(TAG, "DragView(Context context, AttributeSet attrs, int defStyleAttr)");
        init();

    }

    private void init() {
        Log.i(TAG, "init");
        //其中第一个参数指的当前的ViewGroup对象，第二个sensitivity参数指的是对滑动检测的灵敏度，越大越敏感，所需触发滑动的距离越小，默认传1.0f 即可。
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new CallBack());
    }

    /**
     * 计算所有ChildView的宽度和高度 然后根据ChildView的计算结果，设置自己的宽和高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure");

        /**
         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);


        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;

        if (cCount == 0) {
            setMeasuredDimension(0, 0);
        }

        View childView;
        for (int i = 0; i < cCount; i++) {
            childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            setMeasuredDimension(cWidth + i, cHeight + i);
        }

    }


    //    /**
    //     * 计算所有ChildView的宽度和高度 然后根据ChildView的计算结果，设置自己的宽和高
    //     */
    //    @Override
    //    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    //    {
    //        /**
    //         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
    //         */
    //        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    //        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    //        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
    //        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
    //
    //
    //        // 计算出所有的childView的宽和高
    //        measureChildren(widthMeasureSpec, heightMeasureSpec);
    //        /**
    //         * 记录如果是wrap_content是设置的宽和高
    //         */
    //        int width = 0;
    //        int height = 0;
    //
    //        int cCount = getChildCount();
    //
    //        int cWidth = 0;
    //        int cHeight = 0;
    //        MarginLayoutParams cParams = null;
    //
    //        // 用于计算左边两个childView的高度
    //        int lHeight = 0;
    //        // 用于计算右边两个childView的高度，最终高度取二者之间大值
    //        int rHeight = 0;
    //
    //        // 用于计算上边两个childView的宽度
    //        int tWidth = 0;
    //        // 用于计算下面两个childiew的宽度，最终宽度取二者之间大值
    //        int bWidth = 0;
    //
    //        /**
    //         * 根据childView计算的出的宽和高，以及设置的margin计算容器的宽和高，主要用于容器是warp_content时
    //         */
    //        for (int i = 0; i < cCount; i++)
    //        {
    //            View childView = getChildAt(i);
    //            cWidth = childView.getMeasuredWidth();
    //            cHeight = childView.getMeasuredHeight();
    //            cParams = (MarginLayoutParams) childView.getLayoutParams();
    //
    //            // 上面两个childView
    //            if (i == 0 || i == 1)
    //            {
    //                tWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
    //            }
    //
    //            if (i == 2 || i == 3)
    //            {
    //                bWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
    //            }
    //
    //            if (i == 0 || i == 2)
    //            {
    //                lHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
    //            }
    //
    //            if (i == 1 || i == 3)
    //            {
    //                rHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
    //            }
    //
    //        }
    //
    //        width = Math.max(tWidth, bWidth);
    //        height = Math.max(lHeight, rHeight);
    //
    //        /**
    //         * 如果是wrap_content设置为我们计算的值
    //         * 否则：直接设置为父容器计算的值
    //         */
    //        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
    //                : width, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
    //                : height);
    //    }
    //
    //


    /**
     * 对其所有childView进行定位（设置childView的绘制区域）
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i(TAG, "onLayout,changed:" + changed + ",l:" + l + ",t:" + t + ",r:" + r + ",b:" + b);

        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;

        View childView;
        for (int i = 0; i < cCount; i++) {
            childView = getChildAt(i);

            if (childView.getVisibility() != GONE) {
                cWidth = childView.getMeasuredWidth();
                cHeight = childView.getMeasuredHeight();
                childView.layout(0, 0, cWidth, cHeight);
            }
        }

    }

   /* @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;
        */

    /**
     * 遍历所有childView根据其宽和高，以及margin进行布局
     *//*
        for (int i = 0; i < cCount; i++)
        {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            int cl = 0, ct = 0, cr = 0, cb = 0;

            switch (i)
            {
                case 0:
                    cl = cParams.leftMargin;
                    ct = cParams.topMargin;
                    break;
                case 1:
                    cl = getWidth() - cWidth - cParams.leftMargin
                            - cParams.rightMargin;
                    ct = cParams.topMargin;

                    break;
                case 2:
                    cl = cParams.leftMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;
                case 3:
                    cl = getWidth() - cWidth - cParams.leftMargin
                            - cParams.rightMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;

            }
            cr = cl + cWidth;
            cb = cHeight + ct;
            childView.layout(cl, ct, cr, cb);
        }

    }*/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw");
        canvas.drawColor(Color.RED);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent");
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent");
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    private class CallBack extends ViewDragHelper.Callback {
        /**
         * 可用于自由判定哪个子控件可被拖拽
         *
         * @param child
         * @param pointerId
         * @return 返回true代表可拖拽，false则禁止。
         */
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {

            return false;
        }

        /**
         * 子控件水平方向位置改变时触发
         * <p>
         * 该方法决定被拖拽的View在水平方向上应该移动到的位置。
         *
         * @param child 被拖拽的View
         * @param left  期望移动到位置的View的left值
         * @param dx    移动的水平距离
         * @return 直接决定View在水平方向的位置
         */
        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return super.clampViewPositionHorizontal(child, left, dx);
        }

        /**
         * 子控件竖直方向位置改变时触发
         * 该方法决定被拖拽的View在垂直方向上应该移动到的位置。
         *
         * @param child 被拖拽的View
         * @param top   期望移动到位置的View的top值
         * @param dy    移动的垂直距离
         * @return 直接决定View在垂直方向的位置
         */
        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return super.clampViewPositionVertical(child, top, dy);
        }

        /**
         * 当View的拖拽状态改变时，回调该方法。state有三种状态：
         * STATE_IDLE = 0    当前处于闲置状态
         * STATE_DRAGGING = 1   正在被拖拽的状态
         * STATE_SETTLING = 2   拖拽后被安放到一个位置中的状态
         *
         * @param state
         */
        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        /**
         * View被拖拽，位置发生改变时回调
         *
         * @param changedView 被拖拽的View
         * @param left        被拖拽后 View的 left 坐标
         * @param top         被拖拽后 View的 top 坐标
         * @param dx          拖动的x偏移量
         * @param dy          拖动的y偏移量
         */
        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }

        /**
         * 当子控件被捕获到准备开始拖动时回调
         *
         * @param capturedChild   捕获的View
         * @param activePointerId 对应的PointerId
         */
        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        /**
         * 当被捕获拖拽的View被释放时回调
         * 手松开的时候出发
         *
         * @param releasedChild 被释放的View
         * @param xvel          释放View的x方向上的加速度
         * @param yvel          释放View的y方向上的加速度
         */
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
        }


        /**
         * 如果parentView订阅了边缘触摸,则如果有边缘触摸就回调的接口
         *
         * @param edgeFlags 当前触摸的flag 有: EDGE_LEFT,EDGE_TOP,EDGE_RIGHT,EDGE_BOTTOM
         * @param pointerId 用来描述边缘触摸操作的id
         */
        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);

        }

        /**
         * 是否锁定该边缘的触摸
         *
         * @param edgeFlags
         * @return 默认返回false, 返回true表示锁定
         */
        @Override
        public boolean onEdgeLock(int edgeFlags) {
            return super.onEdgeLock(edgeFlags);
        }

        /**
         * 边缘触摸开始时回调
         *
         * @param edgeFlags 当前触摸的flag 有: EDGE_LEFT,EDGE_TOP,EDGE_RIGHT,EDGE_BOTTOM
         * @param pointerId 用来描述边缘触摸操作的id
         */
        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
        }

        /**
         * 在寻找当前触摸点下的子View时会调用此方法，寻找到的View会提供给tryCaptureViewForDrag()来尝试捕获。
         * 如果需要改变子View的遍历查询顺序可改写此方法，例如让下层的View优先于上层的View被选中。
         *
         * @param index
         * @return
         */
        @Override
        public int getOrderedChildIndex(int index) {
            return super.getOrderedChildIndex(index);
        }

        /**
         * 获取被拖拽View child 的水平拖拽范围,
         *
         * @param child
         * @return 返回0表示无法被水平拖拽
         */
        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return super.getViewHorizontalDragRange(child);
        }

        /**
         * 获取被拖拽View child 的竖直拖拽范围,
         *
         * @param child
         * @return 返回0表示无法被竖直拖拽
         */
        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            return super.getViewVerticalDragRange(child);
        }
    }


}
