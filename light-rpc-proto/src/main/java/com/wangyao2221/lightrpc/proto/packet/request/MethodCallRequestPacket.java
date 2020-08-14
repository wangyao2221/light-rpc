package com.wangyao2221.lightrpc.proto.packet.request;

import com.wangyao2221.lightrpc.proto.Request;
import com.wangyao2221.lightrpc.proto.packet.Packet;
import com.wangyao2221.lightrpc.proto.packet.command.Command;
import lombok.Data;

/**
 * @author wangyao2221
 * @date 2020/7/30 22:24
 */
@Data
public class MethodCallRequestPacket extends Packet {
    private Request request;

    @Override
    public Byte getCommand() {
        return Command.METHOD_CALL_REQUEST;
    }
}
