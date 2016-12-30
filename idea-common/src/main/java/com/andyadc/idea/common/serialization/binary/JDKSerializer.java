package com.andyadc.idea.common.serialization.binary;

import com.andyadc.idea.common.serialization.SerializerException;
import org.apache.commons.lang3.ArrayUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author andaicheng
 * @version 2016/12/30
 */
public class JDKSerializer {

    public static <T> byte[] serialize(T object) {
        if (object == null) {
            throw new SerializerException("Object is null");
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        byte[] bytes = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            bytes = baos.toByteArray();
        } catch (Exception e) {
            throw new SerializerException(e.getMessage(), e);
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (Exception e) {

            } finally {
                oos = null;
            }

            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {

            } finally {
                baos = null;
            }

        }

        return bytes;
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] bytes) {
        if (ArrayUtils.isEmpty(bytes)) {
            throw new SerializerException("Bytes is null or empty");
        }

        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        Object object = null;
        try {
            ois = new ObjectInputStream(bais);
            object = ois.readObject();
        } catch (Exception e) {
            throw new SerializerException(e.getMessage(), e);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (Exception e) {

            } finally {
                ois = null;
            }

            try {
                if (bais != null) {
                    bais.close();
                }
            } catch (Exception e) {

            } finally {
                bais = null;
            }
        }

        return (T) object;
    }
}
