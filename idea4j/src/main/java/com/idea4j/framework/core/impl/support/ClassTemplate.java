package com.idea4j.framework.core.impl.support;

import com.idea4j.framework.util.ClassUtil;
import com.idea4j.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 用于获取类的模板类
 *
 * @author andaicheng
 */
public abstract class ClassTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassTemplate.class);

    protected final String packageName;

    protected ClassTemplate(String packageName) {
        this.packageName = packageName;
    }

    public final List<Class<?>> getClassList() {
        List<Class<?>> classList = new ArrayList<>();
        try {
            // 从包名获取 URL 类型的资源
            Enumeration<URL> urls = ClassUtil.getClassLoader().getResources(packageName.replace(".", "/"));
            // 遍历 URL 资源
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    // 获取协议名（分为 file 与 jar）
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        // 若在 class 目录中，则执行添加类操作
                        String packagePath = url.getPath().replaceAll("%20", " ");
                        addClass(classList, packagePath, packageName);
                    } else if ("jar".equals(protocol)) {
                        // 若在 jar 包中，则解析 jar 包中的 entry
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        JarFile jarFile = jarURLConnection.getJarFile();
                        Enumeration<JarEntry> jarEntries = jarFile.entries();
                        while (jarEntries.hasMoreElements()) {
                            JarEntry jarEntry = jarEntries.nextElement();
                            String jarEntryName = jarEntry.getName();
                            // 判断该 entry 是否为 class
                            if (jarEntryName.endsWith(".class")) {
                                // 获取类名
                                String className = jarEntryName.substring(0, jarEntryName.lastIndexOf('.')).replaceAll("/", ".");
                                // 执行添加类操作
                                doAddClass(classList, className);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("获取类出错！", e);
        }
        return classList;
    }

    private void addClass(List<Class<?>> classList, String packagePath, String packageName) {
        try {
            // 获取包名路径下的 class 文件或目录
            /**
             * <pre>
             * File[] files = new File(packagePath).listFiles(new FileFilter() {
             *      @Override
             *      public boolean accept(File file) {
             *          return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
             *      }
             * });
             * <pre>
             */
            //lambda
            File[] files = new File(packagePath).listFiles((File f) -> (f.isFile() && f.getName().endsWith(".class")) || f.isDirectory());
            if (files == null)
                return;

            // 遍历文件或目录
            for (File file : files) {
                String fileName = file.getName();
                // 判断是否为文件或目录
                if (file.isFile()) {
                    // 获取类名
                    String className = fileName.substring(0, fileName.lastIndexOf('.'));
                    if (StringUtil.isNotBlank(packageName)) {
                        className = packageName + "." + className;
                    }
                    // 执行添加类操作
                    doAddClass(classList, className);
                } else {
                    // 获取子包
                    String subPackagePath = fileName;
                    if (StringUtil.isNotBlank(packagePath)) {
                        subPackagePath = packagePath + "/" + subPackagePath;
                    }
                    // 子包名
                    String subPackageName = fileName;
                    if (StringUtil.isNotBlank(packageName)) {
                        subPackageName = packageName + "." + subPackageName;
                    }
                    // 递归调用
                    addClass(classList, subPackagePath, subPackageName);
                }
            }
        } catch (Exception e) {
            LOGGER.error("添加类出错！", e);
        }
    }

    private void doAddClass(List<Class<?>> classList, String className) {
        // 加载类
        Class<?> cls = ClassUtil.loadClass(className, false);
        // 判断是否可以添加类
        if (checkAddClass(cls)) {
            // 添加类
            classList.add(cls);
        }
    }

    /**
     * 验证是否允许添加类
     */
    public abstract boolean checkAddClass(Class<?> cls);
}
