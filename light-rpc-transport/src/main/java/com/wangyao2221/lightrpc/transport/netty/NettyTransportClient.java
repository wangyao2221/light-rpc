package com.wangyao2221.lightrpc.transport.netty;

import com.wangyao2221.lightrpc.proto.Peer;
import com.wangyao2221.lightrpc.proto.Request;
import com.wangyao2221.lightrpc.proto.Response;
import com.wangyao2221.lightrpc.proto.ServiceDescriptor;
import com.wangyao2221.lightrpc.proto.codec.*;
import com.wangyao2221.lightrpc.proto.common.ReflectionUtils;
import com.wangyao2221.lightrpc.proto.packet.request.MethodCallRequestPacket;
import com.wangyao2221.lightrpc.transport.RequestHandler;
import com.wangyao2221.lightrpc.transport.TransportClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyao2221
 * @date 2020/8/5 14:23
 */
@Slf4j
public class NettyTransportClient implements TransportClient {
    private static int MAX_RETRY = 5;
    private final UnprocessedRequests unprocessedRequests;
    private Channel channel;
    private Encoder encoder;
    private Decoder decoder;

    public NettyTransportClient() {
        unprocessedRequests = ReflectionUtils.newInstance(UnprocessedRequests.class);
    }

    @Override
    public void connect(Peer peer) {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new Spliter())
                                .addLast((ByteToMessageDecoder)decoder)
                                .addLast(new MethodCallResponseHandler())
                                .addLast((MessageToByteEncoder)encoder);
                    }
                });

        try {
            channel = connect(bootstrap, peer.getHost(), peer.getPort(), MAX_RETRY);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(Encoder encoder, Decoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    private static Channel connect(Bootstrap bootstrap, String host, int port, int retry) throws ExecutionException, InterruptedException {
        CompletableFuture<Channel> channelFuture = new CompletableFuture<>();
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功！");
                Channel channel = ((ChannelFuture) future).channel();
                channelFuture.complete(channel);
            } else if (retry == 0) {
                System.out.println("重试次数已用完，放弃连接！");
                channelFuture.completeExceptionally(new Throwable("connect fail!"));
            } else {
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                System.out.println(new Date() + "：连接失败，第" + order + "次重连...");
                bootstrap
                        .config()
                        .group()
                        .schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });

        return channelFuture.get();
    }

    @Override
    public Object write(Object data) throws ExecutionException, InterruptedException {
        Request request = (Request) data;
        request.setRequestId("request" + UUID.randomUUID());
        MethodCallRequestPacket methodCallRequestPacket = new MethodCallRequestPacket();
        methodCallRequestPacket.setRequest(request);

        CompletableFuture resultFuture = new CompletableFuture();
        if (channel != null && channel.isActive()) {
            unprocessedRequests.put(request.getRequestId(), resultFuture);
            channel.writeAndFlush(methodCallRequestPacket).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        log.info("client send message: [{}]", request);
                    } else {
                        future.channel().close();
                        resultFuture.completeExceptionally(new Throwable("call method fail!"));
                        log.info("Send failed:", future.cause());
                    }
                }
            });
        } else {
            throw new IllegalStateException();
        }

        Response response = (Response) resultFuture.get();
        return response;
    }

    @Override
    public void close() {

    }
}
