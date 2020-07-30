package com.wangyao2221.lightrpc.proto.codec;

import com.alibaba.fastjson.JSON;

/**
 * @author wangyao2221
 * @date 2020/6/26 22:22
 */
public class JSONDecoder implements Decoder {
    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}
