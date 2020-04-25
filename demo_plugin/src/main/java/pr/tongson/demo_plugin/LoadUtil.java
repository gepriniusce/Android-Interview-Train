package pr.tongson.demo_plugin;

import android.content.Context;
import android.os.Build;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * <b>Create Date:</b> 2020-02-11<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class LoadUtil {

    private final static String apkPath = "/sdcard/plugin-debug.apk";

    public static void loadClass(Context context) {
        // dexElements 的 Field 對象
        try {
            //1.獲取pathList的字段
            Class<?> baseDexClassLoader = Class.forName("dalvik.system.BaseDexClassLoader");
            Field pathListField = baseDexClassLoader.getDeclaredField("pathList");
            pathListField.setAccessible(true);

            /**
             * 獲取插件的dexElements[]
             */
            //2.獲取DexClassLoader類中的屬性pathList的值
            DexClassLoader dexClassLoader = new DexClassLoader(apkPath, context.getCacheDir().getAbsolutePath(), null, context.getClassLoader());
            Object pluginPathList = pathListField.get(dexClassLoader);

            //3.獲取pathList中的屬性dexElements[]的值---插件的dexElements[]
            Class<?> pluginPathListClass = pluginPathList.getClass();
            Field pluginDexElementsField = pluginPathListClass.getDeclaredField("dexElements");
            pluginDexElementsField.setAccessible(true);
            Object[] pluginDexElements = (Object[]) pluginDexElementsField.get(pluginPathList);

            /**
             * 獲取宿主的dexElements[]
             */
            //4.獲取pathClassLoader類中的屬性pathList的值
            PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
            Object hostPathList = pathListField.get(pathClassLoader);

            //5.獲取pathList中的屬性dexElements[]的值---宿主的dexElements[]
            Class hostPathListClass = hostPathList.getClass();
            Field hostDexElementsField = hostPathListClass.getDeclaredField("dexElements");
            hostDexElementsField.setAccessible(true);
            Object[] hostDexElements = (Object[]) hostDexElementsField.get(hostPathList);

            /**
             * 將插件的dexElements[]和宿主的dexElements[]合併為一個新的dexElements[]
             */
            //6.創建一個新的空數組，第一個參數是數組的類型，第二個參數是數組的長度
            Object[] dexElements = (Object[]) Array.newInstance(hostDexElements.getClass().getComponentType(), pluginDexElements.length + hostDexElements.length);

            //7.將插件和宿主的dexElements[]的值放入新的數組中
            System.arraycopy(pluginDexElements, 0, dexElements, 0, pluginDexElements.length);
            System.arraycopy(hostDexElements, 0, dexElements, pluginDexElements.length, hostDexElements.length);

            /**
             * 將生成的新值賦給"dexElements"屬性
             */
            hostDexElementsField.set(hostPathList, dexElements);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
