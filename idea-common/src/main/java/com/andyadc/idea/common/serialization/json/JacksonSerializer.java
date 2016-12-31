package com.andyadc.idea.common.serialization.json;

import com.andyadc.idea.common.IdeaConstants;
import com.andyadc.idea.common.serialization.SerializerException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;

/**
 * @author andaicheng
 * @version 2016/12/30
 */
public class JacksonSerializer {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat(IdeaConstants.DATE_FORMAT));
    }

    private JacksonSerializer() {
    }

    public static <T> String toJson(T object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new SerializerException(e.getMessage(), e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            throw new SerializerException("Json is null or empty");
        }

        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new SerializerException(e.getMessage(), e);
        }
    }
}
