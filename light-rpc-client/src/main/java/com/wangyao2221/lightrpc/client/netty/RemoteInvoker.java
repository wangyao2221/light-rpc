package com.wangyao2221.lightrpc.client.netty;

import com.wangyao2221.lightrpc.client.TransportSelector;
import com.wangyao2221.lightrpc.proto.Request;
import com.wangyao2221.lightrpc.proto.Response;
import com.wangyao2221.lightrpc.proto.ServiceDescriptor;
import com.wangyao2221.lightrpc.transport.netty.NettyTransportClient;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;

/**
 * 调用远程服务的代理类
 *
 * @author wangyao2221
 * @date 2020/6/27 09:29
 */
@Slf4j
public class RemoteInvoker implements InvocationHandler {
    private Class clazz;
    private TransportSelector selector;

    public RemoteInvoker(Class clazz,
                         TransportSelector selector) {
        this.clazz = clazz;
        this.selector = selector;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request request = new Request();
        request.setService(ServiceDescriptor.from(clazz, method));
        request.setParameters(args);

        Response response = invokeRemote(request);
        if (response == null ||response.getCode() != 0) {
            throw new IllegalStateException("fail to invoke remote:" + response.getMessage());
        }

        return response.getData();
    }

    private Response invokeRemote(Request request) {
        Response response = null;
        NettyTransportClient client = null;

        try {
            client = (NettyTransportClient) selector.select();
            response = (Response) client.write(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                selector.release(client);
            }
        }

        return response;
    }
}
