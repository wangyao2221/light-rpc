package com.wangyao2221.lightrpc.example;

import com.wangyao2221.lightrpc.client.netty.RpcClient;

/**
 * @author wangyao2221
 * @date 2020/6/27 09:43
 */
public class Client {
    public static void main(String[] args) {
        RpcClient client = new RpcClient();
        CalcService service = client.getProxy(CalcService.class);
        int r1 = service.add(1, 2);
        int r2 = service.minus(10,8);
        int r3 = service.add(10,8);
        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);
    }
}
