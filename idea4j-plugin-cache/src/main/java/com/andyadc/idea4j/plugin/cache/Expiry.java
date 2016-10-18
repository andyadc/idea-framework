package com.andyadc.idea4j.plugin.cache;

/**
 * @author andaicheng
 * @version 2016/10/18
 */
public enum Expiry {

    ETERNAL(-1L), ZERO(0), ONE_MINUTE(60 * 1000L), FIVE_MINUTES(5 * 60 * 1000L), ONE_HOUR(60 * 60 * 1000L), ONE_DAY(24 * 60 * 60 * 1000L);

    private long duration;

    Expiry(long duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }
}
