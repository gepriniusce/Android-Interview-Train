package pr.tongson.train_event;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * <b>Create Date:</b> 2020-03-12<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class ViewGroup extends View {

    List<View> childList = new ArrayList<>();

    private View[] mChildren = new View[0];
    private TouchTarget mFirsttouchTarget;

    public ViewGroup(int left, int top, int right, int bottom) {
        super(left, top, right, bottom);
    }

    public void addView(View view) {
        if (view == null) {
            return;
        }
        childList.add(view);
        mChildren = (View[]) childList.toArray(new View[childList.size()]);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println(name + " dispatchTouchEvent事件");

        boolean intercept = onInterceptTouchEvent(ev);
        boolean handled = false;
        //內存緩存
        TouchTarget newTouchTarget = null;
        int actionMasked = ev.getActionMasked();
        //如果沒有攔截
        if (actionMasked != MotionEvent.ACTION_CANCEL && !intercept) {
            if (actionMasked == MotionEvent.ACTION_DOWN) {
                //遍歷子view,耗時
                for (int i = mChildren.length - 1; i >= 0; i--) {
                    View child = mChildren[i];
                    //View能夠接收到事件
                    if (!child.isContainer(ev.getX(), ev.getY())) {
                        continue;
                    }

                    //能夠接收事件，child分發給他
                    if (dispatchTransformedTouchEvent(ev, child)) {
                        //View[] 採取了Message的方式進行
                        //有人消費了
                        handled = true;
                        newTouchTarget = addTouchTarget(child);
                        break;
                    }


                }
            }
            //沒有人接收當前事件
            if (mFirsttouchTarget == null) {
                handled = dispatchTransformedTouchEvent(ev, null);
            }

        }
        return handled;
    }

    private TouchTarget addTouchTarget(View child) {
        final TouchTarget target = TouchTarget.obtain(child);
        target.next = mFirsttouchTarget;
        mFirsttouchTarget = target;
        return target;
    }


    private static final class TouchTarget {

        /**
         * 當前緩存的View
         */
        public View child;

        /**
         * 回收池，通過這個對象就可以拿到這個鏈
         */
        private static TouchTarget sRecycleBin;

        public TouchTarget next;

        private static final Object sRecycleLock = new Object();

        private static int sRecycledCount;

        public static TouchTarget obtain(View child) {
            TouchTarget target;
            //對象鎖
            synchronized (sRecycleLock) {
                if (sRecycleBin == null) {
                    target = new TouchTarget();
                } else {
                    //up事件回收
                    target = sRecycleBin;
                }
                sRecycleBin = target.next;
                sRecycledCount--;
                target.next = null;
            }
            target.child = child;
            return target;
        }


        public void recycle() {
            if (child == null) {
                throw new IllegalStateException("已經被回收過了");
            }

            synchronized (sRecycleLock) {
                if (sRecycledCount < 32) {
                    next = sRecycleBin;
                    sRecycleBin = this;
                    sRecycledCount += 1;
                }
            }
        }


    }


    /**
     * 分發處理，子控件 View
     *
     * @param ev
     * @param child
     * @return
     */
    private boolean dispatchTransformedTouchEvent(MotionEvent ev, View child) {
        boolean handled = false;

        if (child != null) {
            //交給ChildView消費
            handled = child.dispatchTouchEvent(ev);
        } else {
            //自己消費
            handled = super.dispatchTouchEvent(ev);
        }
        return handled;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }


    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
