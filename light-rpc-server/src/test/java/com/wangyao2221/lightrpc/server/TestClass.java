package com.wangyao2221.lightrpc.server;

/**
 * @author wangyao2221
 * @date 2020/6/26 23:53
 */
public class TestClass implements TestInterface {
    @Override
    public void hello() {
        System.out.println("hello");
    }
}
