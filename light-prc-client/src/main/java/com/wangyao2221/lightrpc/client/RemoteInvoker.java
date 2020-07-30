package com.wangyao2221.lightrpc.client;

import com.wangyao2221.lightrpc.proto.Request;
import com.wangyao2221.lightrpc.proto.Response;
import com.wangyao2221.lightrpc.proto.ServiceDescriptor;
import com.wangyao2221.lightrpc.proto.codec.Decoder;
import com.wangyao2221.lightrpc.proto.codec.Encoder;
import com.wangyao2221.lightrpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 调用远程服务的代理类
 *
 * @author wangyao2221
 * @date 2020/6/27 09:29
 */
@Slf4j
public class RemoteInvoker implements InvocationHandler {
    private Class clazz;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;

    public RemoteInvoker(Class clazz,
                         Encoder encoder,
                         Decoder decoder,
                         TransportSelector selector) {
        this.clazz = clazz;
        this.encoder = encoder;
        this.decoder = decoder;
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
        TransportClient client = null;

        try {
            client = selector.select();

            byte[] outBytes = encoder.encode(request);
            InputStream receive = client.write(new ByteArrayInputStream(outBytes));

            byte[] inBytes = IOUtils.readFully(receive, receive.available());
            response = decoder.decode(inBytes, Response.class);
        } catch (IOException e) {
            e.printStackTrace();
            response = new Response();
            response.setCode(1);
            response.setMessage("RpcClient got error:"
                    + e.getClass()
                    + " : " + e.getMessage());
        } finally {
            if (client != null) {
                selector.release(client);
            }
        }

        return response;
    }
}
