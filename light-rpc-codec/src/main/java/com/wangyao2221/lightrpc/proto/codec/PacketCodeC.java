package com.wangyao2221.lightrpc.proto.codec;

import com.wangyao2221.lightrpc.proto.ProtocolConstant;
import com.wangyao2221.lightrpc.proto.packet.Packet;
import com.wangyao2221.lightrpc.proto.packet.command.Command;
import com.wangyao2221.lightrpc.proto.packet.request.MethodCallRequestPacket;
import com.wangyao2221.lightrpc.proto.packet.response.MethodCallResponsePacket;
import com.wangyao2221.lightrpc.proto.serializer.Serializer;
import com.wangyao2221.lightrpc.proto.serializer.impl.JsonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangyao2221
 * @date 2020/7/31 11:14
 */
public class PacketCodeC {
    private static final Map<Byte, Class<? extends Packet>> packetMap;
    private static final Map<Byte, Serializer> serializerMap;

    private static class PacketCodeCHandler {
        private final static PacketCodeC INSTANCE = new PacketCodeC();
    }

    protected PacketCodeC() {
    }

    public static PacketCodeC getInstance() {
        return PacketCodeCHandler.INSTANCE;
    }

    static {
        packetMap = new HashMap<>();
        packetMap.put(Command.METHOD_CALL_REQUEST, MethodCallRequestPacket.class);
        packetMap.put(Command.METHOD_CALL_RESPONSE, MethodCallResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = JsonSerializer.getInstance();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public ByteBuf encode(ByteBufAllocator allocator, Packet packet) {
        ByteBuf byteBuf = allocator.ioBuffer();
        byte[] bytes = Serializer.DEFAULT_SERIALIZER.serialize(packet);

        byteBuf.writeInt(ProtocolConstant.MAGIC_NUMBER);
        byteBuf.writeByte(ProtocolConstant.VERSION);
        byteBuf.writeByte(Serializer.DEFAULT_SERIALIZER.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public void encode(ByteBuf byteBuf, Packet packet) {
        byte[] bytes = Serializer.DEFAULT_SERIALIZER.serialize(packet);

        byteBuf.writeInt(ProtocolConstant.MAGIC_NUMBER);
        byteBuf.writeByte(ProtocolConstant.VERSION);
        byteBuf.writeByte(Serializer.DEFAULT_SERIALIZER.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    public Packet decode(ByteBuf byteBuf) {
        byteBuf.skipBytes(4);
        byteBuf.skipBytes(1);

        byte serializerAlgorithm = byteBuf.readByte();
        byte command = byteBuf.readByte();
        int dataLength = byteBuf.readInt();
        byte[] bytes = new byte[dataLength];
        byteBuf.readBytes(bytes);
        Class<? extends Packet> requestType = packetMap.get(command);
        Serializer serializer = getSerializer(serializerAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    public Serializer getSerializer(byte serializerAlgorithm) {
        return serializerMap.get(serializerAlgorithm);
    }
}
