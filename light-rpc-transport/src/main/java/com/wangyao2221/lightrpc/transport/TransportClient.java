package com.wangyao2221.lightrpc.transport;

import com.wangyao2221.lightrpc.proto.Peer;

import java.io.InputStream;

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
    InputStream write(InputStream data);
    void close();
}
