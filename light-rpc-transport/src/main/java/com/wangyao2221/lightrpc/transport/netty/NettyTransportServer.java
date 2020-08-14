package com.wangyao2221.lightrpc.transport.netty;

import com.wangyao2221.lightrpc.proto.codec.*;
import com.wangyao2221.lightrpc.transport.RequestHandler;
import com.wangyao2221.lightrpc.transport.TransportServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author wangyao2221
 * @date 2020/8/5 12:08
 */
public class NettyTransportServer implements TransportServer {
    private int port;
    private RequestHandler handler;
    private Encoder encoder;
    private Decoder decoder;

    public void init(int port, RequestHandler handler, Encoder encoder, Decoder decoder) {
        if (!(handler instanceof MethodCallRequestHandler)) {
            throw new IllegalStateException("please provide a MethodCallRequestHandler instance");
        }
        if (!(encoder instanceof PacketEncoder)) {
            throw new IllegalStateException("please provide a PacketEncoder instance");
        }
        if (!(decoder instanceof PacketDecoder)) {
            throw new IllegalStateException("please provide a PacketDecoder instance");
        }
        init(port, handler);
        this.encoder = encoder;
        this.decoder = decoder;
    }

    @Override
    public void init(int port, RequestHandler handler) {
        this.port = port;
        this.handler = handler;
    }

    @Override
    public void start() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new Spliter())
                                .addLast((PacketDecoder)decoder)
                                .addLast((MethodCallRequestHandler)handler)
                                .addLast((PacketEncoder)encoder);
                    }
                });

        bind(bootstrap, port);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + port + "]绑定成功");
            } else {
                System.out.println("端口[" + port + "]绑定失败");
                bind(serverBootstrap, port + 1);
            }
        });
    }

    @Override
    public void stop() {

    }
}
