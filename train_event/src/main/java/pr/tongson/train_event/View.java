package pr.tongson.train_event;

import pr.tongson.train_event.listener.OnClickListener;
import pr.tongson.train_event.listener.OnTouchListener;

/**
 * <b>Create Date:</b> 2020-03-12<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class View {


    private int left, top, right, bottom;

    private OnTouchListener onTouchListener;

    private OnClickListener onClickListener;

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public View() {
    }

    public View(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public boolean isContainer(int x, int y) {
        if (x >= left && x < right && y >= top && y < bottom) {
            return true;
        }
        return false;
    }


    /**
     * 消費分發代碼
     *
     * @param ev
     * @return
     */
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("View" + " dispatchTouchEvent事件");

        boolean result = false;
        if (onTouchListener != null && onTouchListener.onTouch(this, ev)) {
            result = true;
        }

        if (!result && onTouchEvent(ev)) {
            result = true;
        }

        //影響ViewGroup
        return result;
    }

    private boolean onTouchEvent(MotionEvent ev) {
        if (onClickListener != null) {
            onClickListener.onClick(this);
            //消費了
            return true;
        }
        return false;
    }
}

