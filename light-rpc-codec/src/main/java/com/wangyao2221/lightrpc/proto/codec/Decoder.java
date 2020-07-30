package com.wangyao2221.lightrpc.proto.codec;

/**
 * @author wangyao2221
 * @date 2020/6/26 22:17
 */
public interface Decoder {
    <T> T decode(byte[] bytes, Class<T> clazz);
}
