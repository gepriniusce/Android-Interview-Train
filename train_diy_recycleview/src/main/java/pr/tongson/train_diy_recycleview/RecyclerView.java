package pr.tongson.train_diy_recycleview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Create Date:</b> 2020-03-23<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class RecyclerView extends ViewGroup {
    private Adapter mAdapter;

    /**
     * 当前显示的View
     */
    private List<View> mViewList;
    /**
     * 当前滑动的y值
     */
    private int currentY;
    /**
     * 行数
     */
    private int rowCount;
    /**
     * View的第一行 是占内容的几行
     */
    private int firstRow;
    /**
     * y偏移量
     */
    private int scrollY;
    /**
     * 初始化 第一屏最慢
     */
    private boolean needRelayout;
    private int width;
    private int height;
    /**
     * 高度
     */
    private int[] heights;
    private Recycler mRecycler;

    /**
     * 最小滑动距离
     */
    private int touchSlop;

    public RecyclerView(Context context) {
        super(context);
        init(context);
    }

    public RecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        ViewConfiguration configuration = ViewConfiguration.get(context);
        this.touchSlop = configuration.getScaledTouchSlop();
        this.mViewList = new ArrayList<>();
        this.needRelayout = true;


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int h = 0;
        if (mAdapter != null) {
            this.rowCount = mAdapter.getCount();
            heights = new int[rowCount];
            for (int i = 0; i < heights.length; i++) {
                heights[i] = 200;

            }
        }

        //数据的高度
        int tmpH = sumArray(heights, 0, heights.length);
        h = Math.min(heightSize, tmpH);
        setMeasuredDimension(widthSize, h);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int sumArray(int[] heights, int firstIndex, int count) {
        int sum = 0;
        count += firstIndex;
        for (int i = firstIndex; i < count; i++) {
            sum += heights[i];
        }
        return sum;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (needRelayout || changed) {
            needRelayout = false;
            mViewList.clear();
            removeAllViews();

            if (mAdapter != null) {
                width = r - l;
                height = b - t;
                int left = 0, top = 0, right, bottom;
                //&& top < height;只需要加载这个多个就ok了，复用
                for (int i = 0; i < rowCount && top < height; i++) {
                    right = width;
                    bottom = top + heights[i];
                    //生成一个View
                    mViewList.add(makeAndStep(i, left, top, right, bottom));
                    //循环摆件
                    top = bottom;
                }
            }
        }
    }

    private View makeAndStep(int row, int left, int top, int right, int bottom) {
        View view = obtainView(row, right - left, bottom - top);
        view.layout(left, top, right, bottom);
        return view;
    }

    private View obtainView(int row, int width, int height) {
        int itemType = mAdapter.getItemViewType(row);
        View cacheView = mRecycler.get(itemType);
        View view;
        if (cacheView == null) {
            view = mAdapter.onCreateViewHolder(row, cacheView, this);
            if (view == null) {
                throw new RuntimeException("onCreateViewHolder 必须填充布局");
            }
        } else {
            view = mAdapter.onBinderViewHolder(row, cacheView, this);
        }

        view.setTag(R.id.tag_type_id, itemType);

        //还要测量
        view.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));

        addView(view, 0);

        return view;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int y2 = (int) Math.abs(currentY - ev.getRawY());
                if (y2 > touchSlop) {
                    intercept = true;
                }
                break;
            default:
                break;
        }
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                //移动的距离   y方向
                int y2 = (int) event.getRawY();
                //上滑正，下滑负
                int diffY = currentY - y2;
                scrollBy(0, diffY);
                break;
            }
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private int scrollBounds(int scrollY) {
        //上滑
        if (scrollY > 0) {
            scrollY = Math.min(scrollY, (sumArray(heights, firstRow, heights.length - firstRow) - height));
        }
        //下滑
        else {
            scrollY = Math.max(scrollY, -sumArray(heights, 0, firstRow));
        }
        return scrollY;
    }


    @Override
    public void scrollBy(int x, int y) {
        //scrollY表示 第一个可见Item的左上顶点 距离屏幕的左上顶点的距离
        scrollY += y;
        scrollY = scrollBounds(scrollY);
        //Log.i("Tongson ", "ScrollY:" + scrollY);
        //上滑正 下滑负
        if (scrollY > 0) {
            //1 上滑移除 2 上划加载 3 下滑移除 4 下滑加载

            while (scrollY > heights[firstRow]) {
                removeView(mViewList.remove(0));
                scrollY -= heights[firstRow];
                firstRow++;
            }

            //2 上滑加载
            while (getFillHeight() < height) {
                int addList = firstRow + mViewList.size();
                View view = obtainView(addList, width, heights[addList]);
                mViewList.add(mViewList.size(), view);
            }
        } else if (scrollY < 0) {
            //4 下滑加载
            while (scrollY < 0) {
                int firstAddRow = firstRow - 1;
                View view = obtainView(firstAddRow, width, heights[firstAddRow]);
                mViewList.add(0, view);
                firstRow--;
                scrollY += heights[firstRow + 1];

            }
            //3 下滑移除
            while (sumArray(heights, firstRow, mViewList.size()) - scrollY - heights[firstRow + mViewList.size()-1] >= height) {
                removeView(mViewList.remove(mViewList.size() - 1));
            }

        } else {

        }

        repositionViews();

    }

    /**
     * 摆放所有的Views
     */
    private void repositionViews() {
        int left, top, right, bottom, i;
        top = -scrollY;
        i = firstRow;
        for (View view : mViewList) {
            bottom = top + heights[i++];
            view.layout(0, top, width, bottom);
            top = bottom;
        }

    }

    private int getFillHeight() {
        //数据的高度-scrollY
        return sumArray(heights, firstRow, mViewList.size()) - scrollY;
    }

    @Override
    public void removeView(View view) {
        int key = (int) view.getTag(R.id.tag_type_id);
        //把remove的View缓存起来。
        mRecycler.put(view, key);
        super.removeView(view);
    }

    interface Adapter {
        View onCreateViewHolder(int position, View convertView, ViewGroup parent);

        View onBinderViewHolder(int position, View convertView, ViewGroup parent);

        int getItemViewType(int row);

        int getViewTypeCount();

        int getCount();
    }

    public Adapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        if (mAdapter != null) {
            mRecycler = new Recycler(adapter.getViewTypeCount());
            scrollY = 0;
            firstRow = 0;
            needRelayout = true;
            requestLayout();
        }
    }
}
