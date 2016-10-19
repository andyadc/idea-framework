package com.idea4j.framework.plugin;

import com.idea4j.framework.FrameworkConstant;
import com.idea4j.framework.InstanceFactory;
import com.idea4j.framework.core.ClassScanner;
import com.idea4j.framework.core.fault.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化插件
 *
 * @author andaicheng
 * @version 2016/10/19
 */
public class PluginHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PluginHelper.class);

    /**
     * 创建一个插件列表（用于存放插件实例）
     */
    private static final List<Plugin> PLUGIN_LIST = new ArrayList<>();

    /**
     * 获取 ClassScanner
     */
    private static final ClassScanner CLASS_SCANNER = InstanceFactory.getClassScanner();

    static {
        try {
            // 获取并遍历所有的插件类（实现了 Plugin 接口的类）
            List<Class<?>> pluginClassList = CLASS_SCANNER.getClassListBySuper(FrameworkConstant.PLUGIN_PACKAGE, Plugin.class);
            for (Class<?> plugin : pluginClassList) {

            }
        } catch (Exception e) {
            LOGGER.error("Initial PluginHelper error ", e);
            throw new InitializationError("Initial PluginHelper error ", e);
        }
    }

    /**
     * 获取所有插件
     */
    public static List<Plugin> getPluginList() {
        return PLUGIN_LIST;
    }
}
