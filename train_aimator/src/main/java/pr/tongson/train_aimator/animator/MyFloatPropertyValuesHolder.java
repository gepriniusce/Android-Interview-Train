package pr.tongson.train_aimator.animator;

import android.view.View;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <b>Create Date:</b> 2020-03-12<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class MyFloatPropertyValuesHolder {
    String mPropertyName;

    Class mValueType;

    MyKeyframeSet mMyKeyframeSet;

    Method mSetter = null;

    public MyFloatPropertyValuesHolder(String propertyName, float... values) {
        this.mPropertyName = propertyName;
        mValueType = float.class;

        //sleep(16ms) invidate()信號
        mMyKeyframeSet = MyKeyframeSet.ofFloat(values);
    }

    public void setupSetter(WeakReference<View> target) {
        //變成大寫
        char firstLetter = Character.toUpperCase(mPropertyName.charAt(0));
        String theRest = mPropertyName.substring(1);
        String methodName = "set" + firstLetter + theRest;

        try {
            mSetter = View.class.getMethod(methodName, float.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void setAnimatedValue(View target, float fraction) {
        Float value = mMyKeyframeSet.getValue(fraction);
        try {
            mSetter.invoke(target, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
