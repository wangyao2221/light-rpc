package com.wangyao2221.lightrpc.example;

import com.wangyao2221.lightrpc.server.netty.RpcServer;

/**
 * @author wangyao2221
 * @date 2020/6/27 09:43
 */
public class Server {
    public static void main(String[] args) {
        RpcServer server = new RpcServer();
        server.register(CalcService.class, new CalcServiceImpl());
        server.start();
    }
}
