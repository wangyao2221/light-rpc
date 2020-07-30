package com.wangyao2221.lightrpc.server;

import com.wangyao2221.lightrpc.proto.Request;
import com.wangyao2221.lightrpc.proto.Response;
import com.wangyao2221.lightrpc.proto.codec.Decoder;
import com.wangyao2221.lightrpc.proto.codec.Encoder;
import com.wangyao2221.lightrpc.proto.common.ReflectionUtils;
import com.wangyao2221.lightrpc.transport.RequestHandler;
import com.wangyao2221.lightrpc.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import sun.misc.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
        this.net.init(config.getPort(), handler);
        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecoderClass());
        this.serviceManager = new ServiceManager();
        this.serviceInvoker = new ServiceInvoker();
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

    private RequestHandler handler = new RequestHandler() {
        @Override
        public void onRequest(InputStream receive, OutputStream to) {
            Response response = new Response();
            try {
                byte[] inBytes = IOUtils.readNBytes(receive, receive.available());
                Request request = decoder.decode(inBytes, Request.class);
                log.info("get request: {}", request);

                ServiceInstance sis = serviceManager.lookup(request);
                // sis包含所调用方法必要的信息，request包含方法调用的参数
                // 两者分开解耦，这样sis可以使用重用，针对不同参数进行调用
                Object data = serviceInvoker.invoke(sis, request);
                response.setData(data);
            } catch (IOException e) {
                log.warn(e.getMessage(), e);
                response.setCode(1);
                response.setMessage("RpcServer got error: " + e.getClass().getName() + " : " + e.getMessage());
            } finally {
                try {
                    byte[] outBytes = encoder.encode(response);
                    to.write(outBytes);
                    log.info("response client");
                } catch (IOException e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
    };
}
