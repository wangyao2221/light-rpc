package com.wangyao2221.lightrpc.server.netty;

import com.wangyao2221.lightrpc.proto.codec.*;
import com.wangyao2221.lightrpc.transport.TransportServer;
import com.wangyao2221.lightrpc.transport.netty.NettyTransportServer;
import lombok.Data;

/**
 * server配置
 * @author wangyao2221
 * @date 2020/6/26 23:26
 */
@Data
public class RpcServerConfig {
    private Class<? extends TransportServer> transportCLass = NettyTransportServer.class;
    private Class<? extends Encoder> encoderClass = PacketCodecHandler.class;
    private Class<? extends Decoder> decoderClass = PacketCodecHandler.class;
    private int port = 3000;
}
