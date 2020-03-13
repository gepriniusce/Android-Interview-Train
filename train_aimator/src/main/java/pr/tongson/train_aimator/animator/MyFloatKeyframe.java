package pr.tongson.train_aimator.animator;

/**
 * <b>Create Date:</b> 2020-03-12<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *關鍵幀 保存著 某一時刻的具體狀態  初始化的時候
 * @author tongson
 */
public class MyFloatKeyframe {

    float mFraction;
    Class mValueType;
    float mValue;


    public MyFloatKeyframe(float fraction, float value) {
        mFraction = fraction;
        mValue = value;
        mValueType = float.class;
    }

    public float getValue() {
        return mValue;
    }

    public void setValue(float value) {
        mValue = value;
    }

    public float getFraction() {
        return mFraction;
    }
}
