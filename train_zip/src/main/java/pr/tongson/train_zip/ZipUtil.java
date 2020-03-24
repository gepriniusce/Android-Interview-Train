package pr.tongson.train_zip;

import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
/**
 * <b>Create Date:</b> 2020-03-16<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class ZipUtil {

    private static final String TAG = "Tongson Zip";

    /**
     * @param zipPath 解压文件得路径
     * @param descDir 解压文件目标路径
     */
    @SuppressWarnings({"rawtypes", "resource"})
    public static void unZipFiles(String zipPath, String descDir) throws IOException {
        Log.i(TAG, String.format("文件:{%s}. 解压路径:{%s}. 解压开始.", zipPath, descDir));
        long start = System.currentTimeMillis();
        try {
            File zipFile = new File(zipPath);
            if (!zipFile.exists()) {
                throw new IOException("需解压文件不存在.");
            }
            File pathFile = new File(descDir);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            //ZipFile(String name, Charset charset)
            ZipFile zip = new ZipFile(zipPath);

            Enumeration<?> entries = zip.entries();

            String firstDirectoryName = "";

            //缓存已经处理的文件名字
            ArrayList<String> handles = new ArrayList<>();
            BufferedInputStream bi;

            //遍历
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) entries.nextElement();

                //如果是文件夹
                if (zipEntry.isDirectory()) {
                    //Log.i(TAG, "isDirectory:" + zipEntry.getName());
                    //证明是一级目录
                    int l = zipEntry.getName().length() - zipEntry.getName().replace("/", "").length();
                    if (l == 1) {
                        firstDirectoryName = zipEntry.getName();
                    } else {
                        File file = new File(descDir + "/" + zipEntry.getName());
                        if (!file.exists()) {
                            file.mkdir();
                        }
                    }
                } else {
                    //Log.i(TAG, "isNotDirectory:" + zipEntry.getName());
                    //如果是文件，则write
                    String childName = zipEntry.getName().replaceAll(firstDirectoryName, "");

                    if (handles.contains(childName) && !childName.contains("/")) {
                        //重复名字的文件处理->重命名
                        childName = firstDirectoryName.substring(0, firstDirectoryName.length() - 1) + "-" + childName;
                    } else {
                        handles.add(childName);
                    }

                    File targetFile = new File(descDir + "/" + childName);

                    if (!targetFile.getParentFile().exists()) {
                        targetFile.getParentFile().mkdir();
                        targetFile.createNewFile();
                    }
                    //写入
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    bi = new BufferedInputStream(zip.getInputStream(zipEntry));
                    byte[] readContent = new byte[1024];
                    int readCount = bi.read(readContent);
                    while (readCount != -1) {
                        bos.write(readContent, 0, readCount);
                        readCount = bi.read(readContent);
                    }
                    bos.close();
                }
            }
            Log.i(TAG, String.format("文件:{%s}. 解压路径:{%s}. 解压完成. 耗时:{%s}ms. ", zipPath, descDir, System.currentTimeMillis() - start));
        } catch (Exception e) {
            Log.i(TAG, String.format("文件:{%s}. 解压路径:{%s}. 解压异常:{%s}. 耗时:{%s}ms. ", zipPath, descDir, e, System.currentTimeMillis() - start));
            throw new IOException(e);
        }
    }

}
