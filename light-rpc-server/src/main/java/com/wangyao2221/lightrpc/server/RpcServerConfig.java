package com.wangyao2221.lightrpc.server;

import com.wangyao2221.lightrpc.proto.codec.Decoder;
import com.wangyao2221.lightrpc.proto.codec.Encoder;
import com.wangyao2221.lightrpc.proto.codec.JSONDecoder;
import com.wangyao2221.lightrpc.proto.codec.JSONEncoder;
import com.wangyao2221.lightrpc.transport.HTTPTransportServer;
import com.wangyao2221.lightrpc.transport.TransportServer;
import lombok.Data;

/**
 * server配置
 * @author wangyao2221
 * @date 2020/6/26 23:26
 */
@Data
public class RpcServerConfig {
    private Class<? extends TransportServer> transportCLass = HTTPTransportServer.class;
    private Class<? extends Encoder> encoderClass = JSONEncoder.class;
    private Class<? extends Decoder> decoderClass = JSONDecoder.class;
    private int port = 3000;
}
