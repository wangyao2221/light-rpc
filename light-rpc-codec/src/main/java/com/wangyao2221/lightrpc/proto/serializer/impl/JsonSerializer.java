package com.wangyao2221.lightrpc.proto.serializer.impl;

import com.alibaba.fastjson.JSON;
import com.wangyao2221.lightrpc.proto.serializer.Serializer;
import com.wangyao2221.lightrpc.proto.serializer.SerializerAlgorithm;

/**
 * @author wangyao2221
 * @date 2020/7/30 22:45
 */
public class JsonSerializer implements Serializer {
    private static class JsonSerializerHolder {
        private final static JsonSerializer INSTANCE = new JsonSerializer();
    }

    private JsonSerializer() {
    }

    public static JsonSerializer getInstance() {
        return JsonSerializerHolder.INSTANCE;
    }

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return (T) JSON.parseObject(bytes, clazz);
    }
}
