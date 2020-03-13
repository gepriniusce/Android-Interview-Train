package pr.tongson.train_aimator.animator;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Create Date:</b> 2020-03-12<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class VSYNCManager {
    private static final VSYNCManager ourInstance = new VSYNCManager();

    public static VSYNCManager getInstance() {
        return ourInstance;
    }

    private VSYNCManager() {
        //只有创建这个view的线程才能操作这个view
        new Thread(mRunnable).start();
    }

    private List<AnimationFrameCallback> list = new ArrayList<>();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (AnimationFrameCallback animationFrameCallback : list) {
                    animationFrameCallback.doAnimationFrame(System.currentTimeMillis());
                }
            }
        }
    };

    /**
     * 初始化的時候
     *
     * @param myObjectAnimator
     */
    public void add(MyObjectAnimator myObjectAnimator) {
        list.add(myObjectAnimator);
    }

    interface AnimationFrameCallback {
        boolean doAnimationFrame(long currentTime);
    }


}
