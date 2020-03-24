package pr.tongson.train_scall_a;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.customview.widget.ViewDragHelper;
import pr.tongson.train_scall_a.ui.main.MainFragment;
import pr.tongson.train_scall_a.ui.main.BottomFragment;

/**
 * @author hiphonezhu@gmail.com
 * @version [ViewDragHelperDemo, 16/10/25 15:23]
 */

public class VDHLinearLayout extends LinearLayout {
    FrameLayout topView;
    FrameLayout bottomView;

    int dragBtnHeight;

    public VDHLinearLayout(Context context) {
        super(context);
        init();
    }

    public VDHLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VDHLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    ViewDragHelper dragHelper;
    final int MIN_TOP = 0; // 距离顶部最小的距离

    void init() {
        dragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == bottomView; // 只处理dragBtn
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                if (top > getHeight() - dragBtnHeight) // 底部边界
                {
                    top = getHeight() - dragBtnHeight;
                } else if (top < MIN_TOP) // 顶部边界
                {
                    top = MIN_TOP;
                }
                return top;
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);

                // 改变底部区域高度
                LayoutParams bottomViewLayoutParams = (LayoutParams) bottomView.getLayoutParams();
                bottomViewLayoutParams.height = bottomViewLayoutParams.height + dy * -1;
                bottomView.setLayoutParams(bottomViewLayoutParams);

                // 改变顶部区域高度
//                LayoutParams topViewLayoutParams = (LayoutParams) topView.getLayoutParams();
//                topViewLayoutParams.height = topViewLayoutParams.height + dy;
//                topView.setLayoutParams(topViewLayoutParams);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        topView = (FrameLayout) findViewById(R.id.topView);
        bottomView = (FrameLayout) findViewById(R.id.bottomView);

        if (getContext() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.topView, BottomFragment.newInstance()).commitNow();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.bottomView, MainFragment.newInstance()).commitNow();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        dragBtnHeight = bottomView.getMeasuredHeight();
    }
}
