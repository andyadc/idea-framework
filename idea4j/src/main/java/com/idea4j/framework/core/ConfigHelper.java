package com.idea4j.framework.core;

import com.idea4j.framework.FrameworkConstant;
import com.idea4j.framework.util.PropsUtil;

import java.util.Properties;

/**
 * 获取属性文件中的属性值
 *
 * @author andaicheng
 */
public class ConfigHelper {

    /**
     * 属性文件对象
     */
    private static final Properties configProps = PropsUtil.loadProps(FrameworkConstant.CONFIG_PROPS);

    private ConfigHelper() {
    }

    /**
     * 获取 String 类型的属性值
     */
    public static String getString(String key) {
        return PropsUtil.getString(configProps, key);
    }

    /**
     * 获取 String 类型的属性值（可指定默认值）
     */
    public static String getString(String key, String defaultValue) {
        return PropsUtil.getString(configProps, key, defaultValue);
    }

}
