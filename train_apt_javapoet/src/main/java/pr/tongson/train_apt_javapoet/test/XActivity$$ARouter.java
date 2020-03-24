package pr.tongson.train_apt_javapoet.test;

import pr.tongson.train_apt_javapoet.MainActivity;

/**
 * <b>Create Date:</b> 2020-03-20<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class XActivity$$ARouter {

    public static Class<?> findTargetClass(String path) {
        if (path.equalsIgnoreCase("/app/MainActivity")) {
            return MainActivity.class;
        }

        return null;
    }
}
