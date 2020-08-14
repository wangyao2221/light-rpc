package com.wangyao2221.lightrpc.proto.packet.response;

import com.wangyao2221.lightrpc.proto.Response;
import com.wangyao2221.lightrpc.proto.packet.Packet;
import com.wangyao2221.lightrpc.proto.packet.command.Command;
import lombok.Data;

/**
 * @author wangyao2221
 * @date 2020/8/3 16:39
 */
@Data
public class MethodCallResponsePacket extends Packet {
    private Response response;

    @Override
    public Byte getCommand() {
        return Command.METHOD_CALL_RESPONSE;
    }
}
