package com.wangyao2221.lightrpc.proto.codec;

import com.alibaba.fastjson.JSON;

/**
 * 基于JSON的序列化实现
 * @author wangyao2221
 * @date 2020/6/26 22:19
 */
public class JSONEncoder implements Encoder {
    @Override
    public byte[] encode(Object obj) {
        return JSON.toJSONBytes(obj);
    }
}
