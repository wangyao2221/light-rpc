//package com.wangyao2221.lightrpc.client.netty;
//
//import com.wangyao2221.lightrpc.proto.Peer;
//
//import java.lang.reflect.Proxy;
//
///**
// * @author wangyao2221
// * @date 2020/8/3 20:42
// */
//public class RpcNettyClient {
//    private RpcClientConfig config;
//
//    public RpcNettyClient() {
//        this(new RpcClientConfig());
//    }
//
//    public RpcNettyClient(RpcClientConfig config) {
//        this.config = config;
//    }
//
//    public <T> T getProxy(Class<T> clazz) {
//        return (T) Proxy.newProxyInstance(
//                getClass().getClassLoader(),
//                new Class[]{clazz},
//                new RemoteNettyInvoker(clazz)
//        );
//    }
//}
