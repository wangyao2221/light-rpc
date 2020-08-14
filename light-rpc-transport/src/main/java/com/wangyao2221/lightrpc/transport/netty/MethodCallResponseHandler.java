package com.wangyao2221.lightrpc.transport.netty;

import com.wangyao2221.lightrpc.proto.common.ReflectionUtils;
import com.wangyao2221.lightrpc.proto.packet.Packet;
import com.wangyao2221.lightrpc.proto.packet.response.MethodCallResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangyao2221
 * @date 2020/8/5 15:03
 */
@Slf4j
public class MethodCallResponseHandler extends SimpleChannelInboundHandler<MethodCallResponsePacket> {
    private final UnprocessedRequests unprocessedRequests;

    public MethodCallResponseHandler() {
        this.unprocessedRequests = ReflectionUtils.newInstance(UnprocessedRequests.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MethodCallResponsePacket packet) throws Exception {
        log.info("receive response " + packet.getResponse().getRequestId());
        unprocessedRequests.complete(packet.getResponse());
    }
}
