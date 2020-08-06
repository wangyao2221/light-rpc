package com.wangyao2221.lightrpc.server.netty;

import com.wangyao2221.lightrpc.proto.Request;
import com.wangyao2221.lightrpc.proto.Response;
import com.wangyao2221.lightrpc.proto.codec.Decoder;
import com.wangyao2221.lightrpc.proto.codec.Encoder;
import com.wangyao2221.lightrpc.proto.common.ReflectionUtils;
import com.wangyao2221.lightrpc.server.ServiceInstance;
import com.wangyao2221.lightrpc.server.ServiceInvoker;
import com.wangyao2221.lightrpc.server.ServiceManager;
import com.wangyao2221.lightrpc.transport.RequestHandler;
import com.wangyao2221.lightrpc.transport.TransportServer;
import com.wangyao2221.lightrpc.transport.netty.MethodCallRequestHandler;
import com.wangyao2221.lightrpc.transport.netty.NettyTransportServer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangyao2221
 * @date 2020/6/27 00:08
 */
@Slf4j
public class RpcServer {
    private RpcServerConfig config;
    private TransportServer net;
    private Encoder encoder;
    private Decoder decoder;
    private ServiceManager serviceManager;
    private ServiceInvoker serviceInvoker;

    public RpcServer() {
        this(new RpcServerConfig());
    }

    public RpcServer(RpcServerConfig config) {
        this.config = config;

        this.net = ReflectionUtils.newInstance(config.getTransportCLass());
        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecoderClass());
        this.serviceManager = new ServiceManager();
        this.serviceInvoker = new ServiceInvoker();
        ((NettyTransportServer)this.net).init(config.getPort(), handler, encoder, decoder);
    }

    public <T> void register(Class<T> interfaceClass, T bean) {
        serviceManager.register(interfaceClass, bean);
    }

    public void start() {
        this.net.start();
    }

    public void stop() {
        this.net.stop();
    }

    private RequestHandler handler = new MethodCallRequestHandler() {
        @Override
        public Response handle(Request request) {
            Response response = new Response();

            ServiceInstance sis = serviceManager.lookup(request);
            // sis包含所调用方法必要的信息，request包含方法调用的参数
            // 两者分开解耦，这样sis可以使用重用，针对不同参数进行调用
            Object data = serviceInvoker.invoke(sis, request);
            response.setData(data);

            return response;
        }
    };
}
