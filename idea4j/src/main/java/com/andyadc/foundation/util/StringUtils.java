package com.andyadc.foundation.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A string utility class that manipulates string.
 *
 * @author andaicheng
 * @version 1.0, 2016-10-07
 */
public class StringUtils {

    public static final String EMPTY = "";
    public static final String SPACE = " ";

    private StringUtils() {
    }

    /**
     * <p>Checks if a CharSequence is empty ("") or null.</p>
     * <p>
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param str the CharSequence to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    public static <T extends CharSequence> T defaultIfEmpty(T str, T defaultStr) {
        return isEmpty(str) ? defaultStr : str;
    }

    /**
     * <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
     * <p>
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str the CharSequence to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     */
    public static boolean isBlank(CharSequence str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence str) {
        return !isBlank(str);
    }

    public static <T extends CharSequence> T defaultIfBlank(T str, T defaultStr) {
        return isBlank(str) ? defaultStr : str;
    }

    /**
     * Checks if some CharSequences has any blank string.<br>
     * If strings == null, return true
     *
     * @param strings the CharSequences
     * @return true if any CharSequence is blank, otherwise false.
     */
    public static boolean isAnyBlank(CharSequence... strings) {
        if (strings == null) {
            return true;
        }
        for (CharSequence s : strings) {
            if (isBlank(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if all the CharSequences is not blank.
     *
     * @param strings the CharSequences
     * @return true if all CharSequence is not blank, otherwise false.
     */
    public static boolean isAllNotBlank(CharSequence... strings) {
        return !isAnyBlank(strings);
    }

    /**
     * <p>
     * Checks if some CharSequences has any empty string.
     * </p>
     * If strings == null, return true
     *
     * @param strings the CharSequences
     * @return true is any CharSequence is empty, otherwise false.
     */
    public static boolean isAnyEmpty(CharSequence... strings) {
        if (strings == null) {
            return true;
        }
        for (CharSequence s : strings) {
            if (isEmpty(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if all the CharSequences is not empty.
     *
     * @param strings the CharSequences
     * @return true if all CharSequence is not empty, otherwise false.
     */
    public static boolean isAllNotEmpty(CharSequence... strings) {
        return !isAnyEmpty(strings);
    }

    /**
     * <p>Reverses a String as per {@link StringBuilder#reverse()}.</p>
     * <p>A {@code null} String returns {@code null}.</p>
     * <p>
     * <pre>
     * StringUtils.reverse(null)  = null
     * StringUtils.reverse("")    = ""
     * StringUtils.reverse("bat") = "tab"
     * </pre>
     *
     * @param str the String to reverse, may be null
     * @return the reversed String, {@code null} if null String input
     */
    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * <p>convert the CamelCase String to UnderScoreCase String</p>
     * <p>
     * <pre>
     *     StringUtils.camelCaseToUnderScoreCase("UserId") = "user_id"
     * </pre>
     *
     * @param camelCaseStr CamelCase String
     * @return UnderScoreCase String
     */
    public static String camelCaseToUnderScoreCase(String camelCaseStr) {
        Assert.notNull(camelCaseStr);
        Matcher matcher = Pattern.compile("[A-Z]").matcher(camelCaseStr);
        StringBuilder builder = new StringBuilder(camelCaseStr);
        int i;
        for (i = 0; matcher.find(); ) {
            builder.replace(matcher.start() + i, matcher.end() + i, "_" + matcher.group().toLowerCase());
            i++;
        }
        if (builder.charAt(0) == '_') {
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }

    /**
     * <p>convert the UnderScoreCase String to CamelCase String</p>
     * <p>
     * <pre>
     *     StringUtils.underScoreCaseToCamelCase("user_id") = "userId"
     * </pre>
     *
     * @param underScoreCaseStr UnderScoreCase String
     * @return CamelCase String
     */
    public static String underScoreCaseToCamelCase(String underScoreCaseStr) {
        Assert.notNull(underScoreCaseStr);
        Matcher matcher = Pattern.compile("_[a-z]").matcher(underScoreCaseStr);
        StringBuilder builder = new StringBuilder(underScoreCaseStr);
        int i;
        for (i = 0; matcher.find(); ) {
            builder.replace(matcher.start() - i, matcher.end() - i, matcher.group().substring(1).toUpperCase());
            i++;
        }
        if (Character.isUpperCase(builder.charAt(0))) {
            builder.replace(0, 1, String.valueOf(Character.toLowerCase(builder.charAt(0))));
        }
        return builder.toString();
    }

    /**
     * 替换固定格式的字符串（支持正则表达式）
     */
    public static String replaceAll(String str, String regex, String replacement) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, replacement);
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
