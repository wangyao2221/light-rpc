package com.wangyao2221.lightrpc.proto.codec;

import com.wangyao2221.lightrpc.proto.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * @author wangyao2221
 * @date 2020/8/6 21:09
 */
@ChannelHandler.Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> implements Decoder, Encoder {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> out) throws Exception {
        ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
        PacketCodeC.getInstance().encode(byteBuf, packet);
        out.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        out.add(PacketCodeC.getInstance().decode(byteBuf));
    }

    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        throw new IllegalStateException("com.wangyao2221.lightrpc.proto.codec.Decoder#decode is disabled on PacketCodecHandler");
    }

    @Override
    public byte[] encode(Object obj) {
        throw new IllegalStateException("com.wangyao2221.lightrpc.proto.codec.Encoder#encode is disabled on PacketCodecHandler");
    }
}
