package com.wangyao2221.lightrpc.transport;

import com.wangyao2221.lightrpc.proto.Peer;
import com.wangyao2221.lightrpc.proto.codec.Decoder;
import com.wangyao2221.lightrpc.proto.codec.Encoder;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;

/**
 * 1、创建连接
 * 2、发送数据，并且等待响应
 * 3、关闭连接
 *
 * @author wangyao2221
 * @date 2020/6/26 22:51
 */
public interface TransportClient {
    void connect(Peer peer);
    void init(Encoder encoder, Decoder decoder);
    Object write(Object data) throws ExecutionException, InterruptedException;
    void close();
}
