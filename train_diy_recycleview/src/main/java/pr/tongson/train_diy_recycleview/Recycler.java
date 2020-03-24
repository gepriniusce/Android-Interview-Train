package pr.tongson.train_diy_recycleview;

import android.view.View;

import java.util.Stack;

/**
 * <b>Create Date:</b> 2020-03-23<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class Recycler {
    private Stack<View>[] views;

    public Recycler(int typeNumber) {
        views = new Stack[typeNumber];
        for (int i = 0; i < typeNumber; i++) {
            views[i] = new Stack<>();
        }
    }

    public void put(View view, int type) {
        views[type].push(view);
    }

    public View get(int type) {
        try {
            return views[type].pop();
        } catch (Exception e) {
            return null;
        }
    }
}
