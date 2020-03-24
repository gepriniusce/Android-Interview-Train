package pr.tongson.train_compiler;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import pr.tongson.train_annotation.ARouter;

/**
 * <b>Create Date:</b> 2020-03-20<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({"pr.tongson.train_annotation.ARouter"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedOptions("content")
public class ARouterProcessor extends AbstractProcessor {

    /**
     * 操作Elements工具类
     */
    private Elements mElementsUtils;

    /**
     * Types（类信息）工具类
     */
    private Types mTypeUtils;

    /**
     * 用来输出警告、错误等日志
     */
    private Messager mMessager;

    /**
     * 文件生成器
     */
    private Filer mFiler;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElementsUtils = processingEnvironment.getElementUtils();
        mTypeUtils = processingEnvironment.getTypeUtils();
        mMessager = processingEnvironment.getMessager();
        mFiler = processingEnvironment.getFiler();

        String content = processingEnvironment.getOptions().get("content");

        mMessager.printMessage(Diagnostic.Kind.NOTE, content);
    }

    /**
     * 相当于main函数，开始处理注解
     * 注解处理器的核心方法，处理具体的注解，生成Java文件
     *
     * @param set              使用了支持处理注解的节点集合（类 上面写了注解）
     * @param roundEnvironment 当前或是之前的运行环境,可以通过该对象查找找到的注解。
     * @return true 表示后续处理器不会再处理（已经处理完成）
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "process");

        if (set.isEmpty()) {
            return false;
        }
        // 获取所有带ARouter注解的 类节点
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ARouter.class);


        //遍历所有的类节点
        for (Element e : elements) {
            //包节点
            String packageName = mElementsUtils.getPackageOf(e).getQualifiedName().toString();

            String className = e.getSimpleName().toString();
            mMessager.printMessage(Diagnostic.Kind.NOTE, "被注解的类有：" + className);

            //
            String finalClassName = className + "$$ARouter";

            try {
                JavaFileObject sourceFile = mFiler.createSourceFile(packageName + "." + finalClassName);

                // 定义Writer对象，开启写入
                Writer writer = sourceFile.openWriter();
                // 设置包名
                writer.write("package " + packageName + ";\n");

                writer.write("public class " + finalClassName + " {\n");

                writer.write("public static Class<?> findTargetClass(String path) {\n");

                // 获取类之上@ARouter注解的path值
                ARouter aRouter = e.getAnnotation(ARouter.class);

                writer.write("if (path.equals(\"" + aRouter.path() + "\")) {\n");

                writer.write("return " + className + ".class;\n}\n");

                writer.write("return null;\n");

                writer.write("}\n}");

                // 最后结束别忘了
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }
}
