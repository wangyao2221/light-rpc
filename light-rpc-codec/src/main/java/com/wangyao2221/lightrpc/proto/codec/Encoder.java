package com.wangyao2221.lightrpc.proto.codec;

/**
 * @author wangyao2221
 * @date 2020/6/26 22:17
 */
public interface Encoder {
    byte[] encode(Object obj);
}
