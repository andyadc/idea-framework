package com.idea4j.framework.orm;

import com.idea4j.framework.core.ClassHelper;
import com.idea4j.framework.orm.annotation.Column;
import com.idea4j.framework.orm.annotation.Entity;
import com.idea4j.framework.orm.annotation.Table;
import com.idea4j.framework.util.ArrayUtil;
import com.idea4j.framework.util.MapUtils;
import com.idea4j.framework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始化 Entity 结构
 *
 * @author andaicheng
 */
public class EntityHelper {

    /**
     * 实体类 => 表名
     */
    private static final Map<Class<?>, String> entityClassTableNameMap = new HashMap<>();
    /**
     * 实体类 => (字段名 => 列名)
     */
    private static final Map<Class<?>, Map<String, String>> entityClassFieldMapMap = new HashMap<>();

    static {
        List<Class<?>> entityClassList = ClassHelper.getClassListByAnnotation(Entity.class);
        for (Class<?> entityClass : entityClassList) {
            initEntityNameMap(entityClass);
            initEntityFieldMapMap(entityClass);
        }
    }

    private EntityHelper() {
    }

    private static void initEntityNameMap(Class<?> clazz) {
        String tableName;
        // 判断该实体类上是否存在 Table 注解
        if (clazz.isAnnotationPresent(Table.class)) {
            // 若已存在，则使用该注解中定义的表名
            tableName = clazz.getAnnotation(Table.class).value();
        } else {
            // 若不存在，则将实体类名转换为下划线风格的表名
            tableName = StringUtils.camelCaseToUnderScoreCase(clazz.getSimpleName());
        }
        entityClassTableNameMap.put(clazz, tableName);
    }

    private static void initEntityFieldMapMap(Class<?> clazz) {
        // 获取并遍历该实体类中所有的字段（不包括父类中的方法）
        Field[] fields = clazz.getDeclaredFields();
        if (ArrayUtil.isNotEmpty(fields)) {
            // 创建一个 fieldMap（用于存放列名与字段名的映射关系）
            Map<String, String> fieldMap = new HashMap<>();
            for (Field field : fields) {
                String fieldName = field.getName();
                String columnName;
                // 判断该字段上是否存在 Column 注解
                if (field.isAnnotationPresent(Column.class)) {
                    // 若已存在，则使用该注解中定义的列名
                    columnName = field.getAnnotation(Column.class).value();
                } else {
                    // 若不存在，则将字段名转换为下划线风格的列名
                    columnName = StringUtils.camelCaseToUnderScoreCase(fieldName);
                }
                fieldMap.put(fieldName, columnName);
            }
            entityClassFieldMapMap.put(clazz, fieldMap);
        }
    }

    public static String getTableName(Class<?> entityClass) {
        return entityClassTableNameMap.get(entityClass);
    }

    public static Map<String, String> getFieldMap(Class<?> entityClass) {
        return entityClassFieldMapMap.get(entityClass);
    }

    public static String getColumnName(Class<?> entityClass, String fieldName) {
        return StringUtils.defaultIfBlank(getFieldMap(entityClass).get(fieldName), fieldName);
    }

    public static Map<String, String> getColumnMap(Class<?> entityClass) {
        return MapUtils.reverse(getFieldMap(entityClass));
    }

}
