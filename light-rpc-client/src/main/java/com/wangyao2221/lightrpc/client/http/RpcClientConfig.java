package com.wangyao2221.lightrpc.client.http;

import com.wangyao2221.lightrpc.client.RandomTransportSelector;
import com.wangyao2221.lightrpc.client.TransportSelector;
import com.wangyao2221.lightrpc.proto.Peer;
import com.wangyao2221.lightrpc.proto.codec.Decoder;
import com.wangyao2221.lightrpc.proto.codec.Encoder;
import com.wangyao2221.lightrpc.proto.codec.PacketDecoder;
import com.wangyao2221.lightrpc.proto.codec.PacketEncoder;
import com.wangyao2221.lightrpc.transport.TransportClient;
import com.wangyao2221.lightrpc.transport.netty.NettyTransportClient;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @author wangyao2221
 * @date 2020/6/27 00:52
 */
@Data
public class RpcClientConfig {
    private Class<? extends TransportClient> transportClass = NettyTransportClient.class;
    private Class<? extends Encoder> encoderClass = PacketEncoder.class;
    private Class<? extends Decoder> decoderClass = PacketDecoder.class;
    private Class<? extends TransportSelector> selectorClass = RandomTransportSelector.class;
    private int connectCount = 1;
    private List<Peer> servers = Arrays.asList(
            new Peer("127.0.0.1", 3000)
    );
}
