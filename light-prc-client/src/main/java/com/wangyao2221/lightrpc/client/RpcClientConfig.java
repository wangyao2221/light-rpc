package com.wangyao2221.lightrpc.client;

import com.wangyao2221.lightrpc.proto.Peer;
import com.wangyao2221.lightrpc.proto.codec.Decoder;
import com.wangyao2221.lightrpc.proto.codec.Encoder;
import com.wangyao2221.lightrpc.proto.codec.JSONDecoder;
import com.wangyao2221.lightrpc.proto.codec.JSONEncoder;
import com.wangyao2221.lightrpc.transport.HTTPTransportClient;
import com.wangyao2221.lightrpc.transport.TransportClient;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @author wangyao2221
 * @date 2020/6/27 00:52
 */
@Data
public class RpcClientConfig {
    private Class<? extends TransportClient> transportClass = HTTPTransportClient.class;
    private Class<? extends Encoder> encoderClass = JSONEncoder.class;
    private Class<? extends Decoder> decoderClass = JSONDecoder.class;
    private Class<? extends TransportSelector> selectorClass = RandomTransportSelector.class;
    private int connectCount = 1;
    private List<Peer> servers = Arrays.asList(
            new Peer("127.0.0.1", 3000)
    );
}
