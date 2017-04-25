package com.idea4j.framework.util;

import com.idea4j.framework.FrameworkConstant;
import com.idea4j.framework.FrameworkException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 编码与解码操作工具类
 *
 * @author andaicheng
 */
public class CodecUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);

    private CodecUtil() {
    }

    /**
     * 将 URL 编码
     */
    public static String encodeURL(String str) {
        String target;
        try {
            target = URLEncoder.encode(str, FrameworkConstant.UTF_8);
        } catch (Exception e) {
            LOGGER.error("encode url error", e);
            throw new FrameworkException(e);
        }
        return target;
    }

    /**
     * 将 URL 解码
     */
    public static String decodeURL(String str) {
        String target;
        try {
            target = URLDecoder.decode(str, FrameworkConstant.UTF_8);
        } catch (Exception e) {
            LOGGER.error("decode url error", e);
            throw new FrameworkException(e);
        }
        return target;
    }

    /**
     * 将字符串 Base64 编码
     */
    public static String encodeBASE64(String str) {
        String target;
        try {
            target = Base64.encodeBase64URLSafeString(str.getBytes(FrameworkConstant.UTF_8));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("encode error", e);
            throw new FrameworkException(e);
        }
        return target;
    }

    /**
     * 将字符串 Base64 解码
     */
    public static String decodeBASE64(String str) {
        String target;
        try {
            target = new String(Base64.decodeBase64(str), FrameworkConstant.UTF_8);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("decode error", e);
            throw new FrameworkException(e);
        }
        return target;
    }

    /**
     * 将字符串 MD5 加密
     */
    public static String encryptByMD5(String str) {
        return DigestUtils.md5Hex(str);
    }

    /**
     * 将字符串 SHA1 加密
     */
    public static String encryptBySHA1(String str) {
        return DigestUtils.sha1Hex(str);
    }

    /**
     * 将字符串 SHA256 加密
     */
    public static String encryptBySHA256(String str) {
        return DigestUtils.sha256Hex(str);
    }

    /**
     * 获取 UUID（32位）
     */
    public static String createUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
