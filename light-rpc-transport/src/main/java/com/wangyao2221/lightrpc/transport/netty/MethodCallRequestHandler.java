package com.wangyao2221.lightrpc.transport.netty;

import com.wangyao2221.lightrpc.proto.Request;
import com.wangyao2221.lightrpc.proto.Response;
import com.wangyao2221.lightrpc.proto.packet.request.MethodCallRequestPacket;
import com.wangyao2221.lightrpc.proto.packet.response.MethodCallResponsePacket;
import com.wangyao2221.lightrpc.transport.RequestHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author wangyao2221
 * @date 2020/8/2 23:19
 */
@Slf4j
public abstract class MethodCallRequestHandler extends SimpleChannelInboundHandler<MethodCallRequestPacket> implements RequestHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MethodCallRequestPacket packet) throws Exception {
//        ServiceDescriptor sdp = packet.getRequest().getService();
//        log.info("call " + sdp.getClazz() + "#" + sdp.getMethod());
//        Request request = packet.getRequest();
//        ServiceInstance sis = serviceManager.lookup(request);
//
//        Object data = serviceInvoker.invoke(sis, request);
//        Response response = new Response();
//        response.setData(data);
        log.info("receive request " + packet.getRequest().getRequestId());

        MethodCallResponsePacket responsePacket = new MethodCallResponsePacket();
        Request request = packet.getRequest();
        Response response = handle(request);
        response.setRequestId(request.getRequestId());
        responsePacket.setResponse(response);
        ctx.channel().writeAndFlush(responsePacket);
    }

    public abstract Response handle(Request request);

    @Override
    public void onRequest(InputStream receive, OutputStream to) {
        throw new IllegalStateException("com.wangyao2221.lightrpc.transport.RequestHandler#onRequest is disabled on MethodCallRequestHandler");
    }
}
