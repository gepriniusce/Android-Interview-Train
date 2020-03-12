package pr.tongson.train_event.listener;

import pr.tongson.train_event.MotionEvent;
import pr.tongson.train_event.View;

/**
 * <b>Create Date:</b> 2020-03-12<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public interface OnTouchListener {

    boolean onTouch(View v, MotionEvent event);

}
