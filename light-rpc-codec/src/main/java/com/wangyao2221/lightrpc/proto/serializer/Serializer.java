package com.wangyao2221.lightrpc.proto.serializer;

import com.wangyao2221.lightrpc.proto.serializer.impl.JsonSerializer;

/**
 * @author wangyao2221
 * @date 2020/7/30 22:36
 */
public interface Serializer {
    byte JSON_SERIALIZER = 1;
    Serializer DEFAULT_SERIALIZER = JsonSerializer.getInstance();

    /**
     * 序列化算法
     * @return 序列化算法编码
     */
    byte getSerializerAlgorithm();

    /**
     * 序列化，对象转成二进制
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 反序列化，二进制转成对象
     * @param bytes
     * @return
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
