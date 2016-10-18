package com.andyadc.idea4j.plugin.cache;

/**
 * @author andaicheng
 * @version 2016/10/18
 */
public class Expiry {

    public static final long ETERNAL = -1;
    public static final long ZERO = 0;
    public static final long ONE_MINUTE = 60 * 1000L;
    public static final long FIVE_MINUTES = 5 * ONE_MINUTE;
    public static final long TEN_MINUTES = 10 * ONE_MINUTE;
    public static final long THIRTY_MINUTES = 30 * ONE_MINUTE;
    public static final long ONE_HOUR = 60 * ONE_MINUTE;
    public static final long ONE_DAY = 24 * ONE_HOUR;

}
