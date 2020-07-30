package com.wangyao2221.lightrpc.client;

import com.wangyao2221.lightrpc.proto.Peer;
import com.wangyao2221.lightrpc.transport.TransportClient;

import java.util.List;

/**
 * 表示选择那个server去连接
 *
 * @author wangyao2221
 * @date 2020/6/27 00:40
 */
public interface TransportSelector {
    /**
     * 初始化selector
     *
     * @param peers 可以连接的server端点信息
     * @param count client与server简历多少个连接
     * @param clazz client实现class
     */
    void init(List<Peer> peers,
              int count,
              Class<? extends TransportClient> clazz);

    /**
     * 选择一个transport与server做交互
     *
     * @return 网络client
     */
    TransportClient select();

    /**
     * 释放用完的client
     *
     * @param client
     */
    void release(TransportClient client);

    void close();
}
