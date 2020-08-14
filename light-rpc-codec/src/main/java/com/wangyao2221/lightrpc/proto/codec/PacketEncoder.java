package com.wangyao2221.lightrpc.proto.codec;

import com.wangyao2221.lightrpc.proto.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangyao2221
 * RpcClientConfig中使用的编码器是Encoder接口，因此此处要实现Encoder接口
 * 但实际上Netty中需要的编码器是MessageToByteEncoder，所以需要集成MessageToByteEncoder
 * 所以实际上Encoder的encode应该被禁用，所以在encode的实现中直接抛出一个异常
 * @author wangyao2221
 * @date 2020/8/2 22:49
 */
@Slf4j
public class PacketEncoder extends MessageToByteEncoder<Packet> implements Encoder {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
        log.info("encode data");
        PacketCodeC.getInstance().encode(out, packet);
    }

    @Override
    public byte[] encode(Object obj) {
        throw new IllegalStateException("com.wangyao2221.lightrpc.proto.codec.Encoder#encode is disabled on PacketEncoder");
    }
}
