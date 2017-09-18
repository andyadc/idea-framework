package com.idea4j.framework;

import com.idea4j.framework.core.ConfigHelper;

/**
 * Framework Constants
 *
 * @author andaicheng
 */
public final class FrameworkConstant {

    public static final String UTF_8 = "UTF-8";

    public static final String CONFIG_PROPS = "idea4j.properties";
    public static final String SQL_PROPS = "idea4j-sql.properties";

    public static final String PLUGIN_PACKAGE = "com.idea4j.plugin";

    public static final String STATIC_PATH = ConfigHelper.getString("smart.framework.app.static_path", "/static/");
    public static final String JSP_PATH = ConfigHelper.getString("smart.framework.app.jsp_path", "/views/");
    public static final String HOME_PAGE = ConfigHelper.getString("smart.framework.app.home_page", "/index.html");

    private FrameworkConstant() {
    }

}
