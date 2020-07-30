package com.wangyao2221.lightrpc.transport;

/**
 * 1、启动、监听
 * 2、接受请求
 * 3、关闭监听
 * @author wangyao2221
 * @date 2020/6/26 22:55
 */
public interface TransportServer {
    void init(int port, RequestHandler handler);
    void start();
    void stop();
}
