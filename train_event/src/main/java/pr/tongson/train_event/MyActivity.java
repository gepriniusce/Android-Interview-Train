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
public class MyActivity {

    public static void main(String[] args) {
        ViewGroup viewGroup = new ViewGroup(0, 0, 1080, 1920);
        viewGroup.setName("頂級容器");
        ViewGroup viewGroup2 = new ViewGroup(0, 0, 500, 500);
        viewGroup2.setName("第二級容器");

        View view = new View(0, 0, 200, 200);
        viewGroup2.addView(view);

        viewGroup.addView(viewGroup2);

        viewGroup.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("頂級容器的OnTouch事件");
                return false;
            }
        });
        viewGroup2.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("第二級ViewGroup的OnTouch事件");
                return false;
            }
        });

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("View的OnClick事件");
            }
        });

        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("View的OnTouch事件");
                return false;
            }
        });

        MotionEvent motionEvent = new MotionEvent(100, 100);
        motionEvent.setActionMasked(MotionEvent.ACTION_DOWN);
        viewGroup.dispatchTouchEvent(motionEvent);
    }

}
