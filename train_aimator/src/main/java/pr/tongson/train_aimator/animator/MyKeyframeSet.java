package pr.tongson.train_aimator.animator;

import android.animation.FloatEvaluator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <b>Create Date:</b> 2020-03-12<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
class MyKeyframeSet {

    List<MyFloatKeyframe> mKeyframes;

    MyFloatKeyframe mFirstKeyframe;
    FloatEvaluator mEvaluator;

    public MyKeyframeSet(MyFloatKeyframe[] keyframes) {
        mKeyframes = Arrays.asList(keyframes);
        mFirstKeyframe = keyframes[0];
        mEvaluator = new FloatEvaluator();
    }

    public static MyKeyframeSet ofFloat(float[] values) {
        //        mKeyframes= Arrays.asList(values)
        int numKeyframes = values.length;

        MyFloatKeyframe keyframes[] = new MyFloatKeyframe[numKeyframes];
        keyframes[0] = new MyFloatKeyframe(0, values[0]);
        for (int i = 0; i < numKeyframes; ++i) {
            keyframes[i] = new MyFloatKeyframe((float) i / (numKeyframes - 1), values[i]);
        }
        return new MyKeyframeSet(keyframes);
    }

    public Float getValue(float fraction) {
        MyFloatKeyframe prevKeyframe = mFirstKeyframe;
        for (int i = 0; i < mKeyframes.size(); ++i) {
            MyFloatKeyframe nextKeyframe = mKeyframes.get(i);

            if (fraction < nextKeyframe.getFraction()) {
                //估值器
                return mEvaluator.evaluate(fraction, prevKeyframe.getValue(), nextKeyframe.getValue());
            }

            prevKeyframe = nextKeyframe;
        }
        return fraction;
    }
}
