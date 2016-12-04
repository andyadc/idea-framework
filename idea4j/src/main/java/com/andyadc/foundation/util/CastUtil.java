package com.andyadc.foundation.util;

/**
 * 转型操作工具类
 *
 * @author andaicheng
 */
public class CastUtil {

    private CastUtil() {
    }

    /**
     * 转为 String 型
     */
    public static String castString(Object obj) {
        return castString(obj, "");
    }

    /**
     * 转为 String 型（提供默认值）
     */
    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 转为 double 型
     */
    public static double castDouble(Object obj) {
        return castDouble(obj, 0);
    }

    /**
     * 转为 double 型（提供默认值）
     */
    public static double castDouble(Object obj, double defaultValue) {
        double doubleValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtils.isNotBlank(strValue)) {
                try {
                    doubleValue = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    /**
     * 转为 long 型
     */
    public static long castLong(Object obj) {
        return castLong(obj, 0);
    }

    /**
     * 转为 long 型（提供默认值）
     */
    public static long castLong(Object obj, long defaultValue) {
        long longValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtils.isNotBlank(strValue)) {
                try {
                    longValue = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    longValue = defaultValue;
                }
            }
        }
        return longValue;
    }

    /**
     * 转为 int 型
     */
    public static int castInt(Object obj) {
        return castInt(obj, 0);
    }

    /**
     * 转为 int 型（提供默认值）
     */
    public static int castInt(Object obj, int defaultValue) {
        int intValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtils.isNotBlank(strValue)) {
                try {
                    intValue = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    intValue = defaultValue;
                }
            }
        }
        return intValue;
    }

    /**
     * 转为 boolean 型
     */
    public static boolean castBoolean(Object obj) {
        return castBoolean(obj, false);
    }

    /**
     * 转为 boolean 型（提供默认值）
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean booleanValue = defaultValue;
        if (obj != null) {
            booleanValue = Boolean.parseBoolean(castString(obj));
        }
        return booleanValue;
    }

    /**
     * 转为 String[] 型
     */
    public static String[] castStringArray(Object[] objArray) {
        Object[] newArray = objArray;
        if (objArray == null) {
            newArray = new Object[0];
        }
        String[] strArray = new String[newArray.length];
        if (ArrayUtil.isNotEmpty(newArray)) {
            for (int i = 0; i < newArray.length; i++) {
                strArray[i] = castString(newArray[i]);
            }
        }
        return strArray;
    }

    /**
     * 转为 double[] 型
     */
    public static double[] castDoubleArray(Object[] objArray) {
        Object[] newArray = objArray;
        if (objArray == null) {
            newArray = new Object[0];
        }
        double[] doubleArray = new double[newArray.length];
        if (!ArrayUtil.isEmpty(newArray)) {
            for (int i = 0; i < newArray.length; i++) {
                doubleArray[i] = castDouble(newArray[i]);
            }
        }
        return doubleArray;
    }

    /**
     * 转为 long[] 型
     */
    public static long[] castLongArray(Object[] objArray) {
        Object[] newArray = objArray;
        if (objArray == null) {
            newArray = new Object[0];
        }
        long[] longArray = new long[newArray.length];
        if (!ArrayUtil.isEmpty(newArray)) {
            for (int i = 0; i < newArray.length; i++) {
                longArray[i] = castLong(newArray[i]);
            }
        }
        return longArray;
    }

    /**
     * 转为 int[] 型
     */
    public static int[] castIntArray(Object[] objArray) {
        Object[] newArray = objArray;
        if (objArray == null) {
            newArray = new Object[0];
        }
        int[] intArray = new int[newArray.length];
        if (!ArrayUtil.isEmpty(newArray)) {
            for (int i = 0; i < newArray.length; i++) {
                intArray[i] = castInt(newArray[i]);
            }
        }
        return intArray;
    }

    /**
     * 转为 boolean[] 型
     */
    public static boolean[] castBooleanArray(Object[] objArray) {
        Object[] newArray = objArray;
        if (objArray == null) {
            newArray = new Object[0];
        }
        boolean[] booleanArray = new boolean[newArray.length];
        if (!ArrayUtil.isEmpty(newArray)) {
            for (int i = 0; i < newArray.length; i++) {
                booleanArray[i] = castBoolean(newArray[i]);
            }
        }
        return booleanArray;
    }
}
