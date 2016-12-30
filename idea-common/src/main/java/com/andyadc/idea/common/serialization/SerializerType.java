package com.andyadc.idea.common.serialization;

/**
 * @author andaicheng
 * @version 2016/12/30
 */
public enum SerializerType {
    FST_BINARY("fstBinarySerializer"),
    KRYO_BINARY("kryoBinarySerializer"),
    JDK_BINARY("jdkBinarySerializer"),
    JACKSON_JSON("jacksonJsonSerializer"),
    FAST_JSON("fastJsonSerializer"),
    FST_JSON("fstJsonSerializer");

    private String value;

    SerializerType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SerializerType fromString(String value) {
        for (SerializerType type : SerializerType.values()) {
            if (type.getValue().equalsIgnoreCase(value.trim())) {
                return type;
            }
        }

        throw new IllegalArgumentException("Mismatched type with value = " + value);
    }

    @Override
    public String toString() {
        return value;
    }
}
