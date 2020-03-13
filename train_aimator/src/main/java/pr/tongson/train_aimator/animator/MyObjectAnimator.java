package pr.tongson.train_aimator.animator;


import android.content.Context;
import android.content.Intent;
import android.util.Property;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * <b>Create Date:</b> 2020-03-12<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class MyObjectAnimator implements VSYNCManager.AnimationFrameCallback {

    private WeakReference<View> mTarget;

    MyFloatPropertyValuesHolder mMyFloatPropertyValuesHolder;


    private int index;

    long mStartTime = -1;

    private long mDuration = 0;

    public MyObjectAnimator(View target, String propertyName, float[] values) {
        mTarget = new WeakReference<View>(target);
        mMyFloatPropertyValuesHolder = new MyFloatPropertyValuesHolder(propertyName, values);
    }


    public static MyObjectAnimator ofFloat(View target, String propertyName, float... values) {
        //        MyObjectAnimator anim = new MyObjectAnimator(target, propertyName);
        //        return anim;
        MyObjectAnimator anim = new MyObjectAnimator(target, propertyName, values);
        //        anim.setFloatValues(values);

        return anim;
    }

    private TimeInterpolator interpolator;

    public void setInterpolator(TimeInterpolator interpolator) {
        this.interpolator = interpolator;
    }

    @Override
    public boolean doAnimationFrame(long currentTime) {

        float total = mDuration / 16;
        //執行百分比(index++)/16
        float fraction = (index++) / total;
        if (interpolator != null) {
            //差值器
            fraction = interpolator.getInterpolation(fraction);
        }

        if (index >= total) {
            index = 0;
        }

        mMyFloatPropertyValuesHolder.setAnimatedValue(mTarget.get(), fraction);

        return false;
    }


    public void start() {
        mMyFloatPropertyValuesHolder.setupSetter(mTarget);
        mStartTime = System.currentTimeMillis();
        VSYNCManager.getInstance().add(this);
    }


    public void setDeration(int i) {
        mDuration = i;
    }
}
