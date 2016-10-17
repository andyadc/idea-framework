package com.idea4j.framework.util;

import com.idea4j.framework.FrameworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类操作工具类
 *
 * @author andaicheng
 */
public class ClassUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    private static final String CLASS_SUFFIX = ".class";

    private ClassUtil() {
    }

    public static void main(String[] args) {
        LOGGER.info(getClassPath());
    }

    /**
     * 获取类加载器
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 获取类路径
     */
    public static String getClassPath() {
        String classpath = "";
        URL url = getClassLoader().getResource("");
        if (url != null) {
            classpath = url.getPath();
        }
        return classpath;
    }

    /**
     * 加载类
     *
     * @param className     类名
     * @param isInitialized 是否初始化
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className, isInitialized, getClassLoader());
        } catch (Exception e) {
            LOGGER.error("加载类出错!", e);
            throw new FrameworkException(e);
        }
        return clazz;
    }

    /**
     * 将自动初始化
     *
     * @param className 类名
     */
    public static Class<?> loadClass(String className) {
        return loadClass(className, true);
    }

    /**
     * 获取指定包名下的所有类
     *
     * @param packageName 包名
     * @param isRecursive 是否递归
     */
    public static List<Class<?>> getClassList(String packageName, boolean isRecursive) {
        List<Class<?>> classList = new ArrayList<>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replaceAll("\\.", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        String packagePath = url.getPath();
                        addClass(classList, packagePath, packageName, isRecursive);
                    } else if ("jar".equals(protocol)) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        JarFile jarFile = jarURLConnection.getJarFile();
                        Enumeration<JarEntry> entries = jarFile.entries();
                        while (entries.hasMoreElements()) {
                            JarEntry entry = entries.nextElement();
                            String jarEntryName = entry.getName();
                            if (jarEntryName.endsWith(CLASS_SUFFIX)) {
                                String className = jarEntryName.substring(0, jarEntryName.lastIndexOf('.')).replace("/", ".");
                                if (isRecursive || className.substring(0, className.lastIndexOf('.')).equals(packageName)) {
                                    classList.add(loadClass(className, false));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("获取类出错!", e);
            throw new FrameworkException(e);
        }
        return classList;
    }

    /**
     * 获取指定包名下指定注解的所有类
     *
     * @param packageName     包名
     * @param annotationClass 注解
     */
    public static List<Class<?>> getClassListByAnnotation(String packageName, Class<? extends Annotation> annotationClass) {
        List<Class<?>> classList = new ArrayList<>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replaceAll("\\.", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        String packagePath = url.getPath();
                        addClassByAnnotation(classList, packagePath, packageName, annotationClass);
                    } else if ("jar".equals(protocol)) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        JarFile jarFile = jarURLConnection.getJarFile();
                        Enumeration<JarEntry> entries = jarFile.entries();
                        while (entries.hasMoreElements()) {
                            JarEntry entry = entries.nextElement();
                            String jarEntryName = entry.getName();
                            if (jarEntryName.endsWith(CLASS_SUFFIX)) {
                                String className = jarEntryName.substring(0, jarEntryName.lastIndexOf('.')).replace("/", ".");
                                Class<?> clazz = Class.forName(className);
                                if (clazz.isAnnotationPresent(annotationClass)) {
                                    classList.add(clazz);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("获取类出错!", e);
            throw new FrameworkException(e);
        }
        return classList;
    }

    /**
     * 获取指定包名下指定父类的所有类
     *
     * @param packageName 包名
     * @param superClass  父类
     */
    public static List<Class<?>> getClassListBySuper(String packageName, Class<?> superClass) {
        List<Class<?>> classList = new ArrayList<>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replaceAll("\\.", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        String packagePath = url.getPath();
                        addClassBySuper(classList, packagePath, packageName, superClass);
                    } else if ("jar".equals(protocol)) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        JarFile jarFile = jarURLConnection.getJarFile();
                        Enumeration<JarEntry> entries = jarFile.entries();
                        while (entries.hasMoreElements()) {
                            JarEntry entry = entries.nextElement();
                            String jarEntryName = entry.getName();
                            if (jarEntryName.endsWith(CLASS_SUFFIX)) {
                                String className = jarEntryName.substring(0, jarEntryName.lastIndexOf('.')).replace("/", ".");
                                Class<?> clazz = Class.forName(className);
                                if (clazz.isAssignableFrom(superClass) && !superClass.equals(clazz)) {
                                    classList.add(clazz);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("获取指定包名下指定父类的所有类!", e);
            throw new FrameworkException(e);
        }
        return classList;
    }

    private static void addClass(List<Class<?>> classList, String packagePath, String packageName, boolean isRecursive) {
        try {
            File[] files = getClassFiles(packagePath);
            if (files != null && files.length > 0) {
                for (File file : files) {
                    String fileName = file.getName();
                    if (file.isFile()) {
                        String className = getClassName(packageName, fileName);
                        classList.add(loadClass(className, false));
                    } else {
                        if (isRecursive) {
                            String subPackagePath = getSubPackagePath(packagePath, fileName);
                            String subPackageName = getSubPackageName(packageName, fileName);
                            addClass(classList, subPackagePath, subPackageName, true);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("添加类出错!", e);
            throw new FrameworkException(e);
        }
    }

    private static void addClassByAnnotation(List<Class<?>> classList, String packagePath, String packageName, Class<? extends Annotation> annotationClass) {
        try {
            File[] files = getClassFiles(packagePath);
            if (files != null && files.length > 0) {
                for (File file : files) {
                    String fileName = file.getName();
                    if (file.isFile()) {
                        String className = getClassName(packageName, fileName);
                        Class<?> clazz = loadClass(className, false);
                        if (clazz.isAnnotationPresent(annotationClass)) {
                            classList.add(clazz);
                        }
                    } else {
                        String subPackagePath = getSubPackagePath(packagePath, fileName);
                        String subPackageName = getSubPackageName(packageName, fileName);
                        addClassByAnnotation(classList, subPackagePath, subPackageName, annotationClass);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("根据注解添加类出错!", e);
            throw new FrameworkException(e);
        }
    }

    private static void addClassBySuper(List<Class<?>> classList, String packagePath, String packageName, Class<?> superClass) {
        try {
            File[] files = getClassFiles(packagePath);
            if (files != null && files.length > 0) {
                for (File file : files) {
                    String fileName = file.getName();
                    if (file.isFile()) {
                        String className = getClassName(packageName, fileName);
                        Class<?> clazz = loadClass(className, false);
                        if (clazz.isAssignableFrom(superClass) && !superClass.equals(clazz)) {
                            classList.add(clazz);
                        }
                    } else {
                        String subPackagePath = getSubPackagePath(packagePath, fileName);
                        String subPackageName = getSubPackageName(packageName, fileName);
                        addClassBySuper(classList, subPackagePath, subPackageName, superClass);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("根据父类添加类出错!", e);
            throw new FrameworkException(e);
        }
    }

    private static File[] getClassFiles(String packagePath) {
        return new File(packagePath).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return (dir.isFile() && dir.getName().endsWith(CLASS_SUFFIX)) || dir.isDirectory();
            }
        });
    }

    private static String getClassName(String packageName, String fileName) {
        String className = fileName.substring(0, fileName.length());
        if (StringUtil.isNotBlank(className)) {
            className = packageName + "." + fileName;
        }
        return className;
    }

    private static String getSubPackagePath(String packagePath, String filePath) {
        String subPackagePath = filePath;
        if (StringUtil.isNotBlank(packagePath)) {
            subPackagePath = packagePath + "/" + subPackagePath;
        }
        return subPackagePath;
    }

    private static String getSubPackageName(String packageName, String filePath) {
        String subPackageName = filePath;
        if (StringUtil.isNotBlank(packageName)) {
            subPackageName = packageName + "." + subPackageName;
        }
        return subPackageName;
    }

    /**
     * 是否为 int 类型（包括 Integer 类型）
     */
    public static boolean isInt(Class<?> type) {
        return type.equals(int.class) || type.equals(Integer.class);
    }

    /**
     * 是否为 long 类型（包括 Long 类型）
     */
    public static boolean isLong(Class<?> type) {
        return type.equals(long.class) || type.equals(Long.class);
    }

    /**
     * 是否为 double 类型（包括 Double 类型）
     */
    public static boolean isDouble(Class<?> type) {
        return type.equals(double.class) || type.equals(Double.class);
    }

    /**
     * 是否为 String 类型
     */
    public static boolean isString(Class<?> type) {
        return type.equals(String.class);
    }
}
