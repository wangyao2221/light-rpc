package com.wangyao2221.lightrpc.proto.codec;

import com.wangyao2221.lightrpc.proto.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author wangyao2221
 * RpcClientConfig中使用的解码器是Decoder接口，因此此处要实现Decoder接口
 * 但实际上Netty中需要的解码器是ByteToMessageDecoder，所以需要集成ByteToMessageDecoder
 * 所以实际上Decoder的decode应该被禁用，所以在decode的实现中直接抛出一个异常
 *
 * @date 2020/8/2 22:56
 */
@Slf4j
public class PacketDecoder extends ByteToMessageDecoder implements Decoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.info("decode data");
        out.add(PacketCodeC.getInstance().decode(in));
    }

    @Override
    public  <T> T decode(byte[] bytes, Class<T> clazz) {
        throw new IllegalStateException("com.wangyao2221.lightrpc.proto.codec.Decoder#decode is disabled on PacketDecoder");
    }
}
