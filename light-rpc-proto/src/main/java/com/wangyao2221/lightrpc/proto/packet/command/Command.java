package com.wangyao2221.lightrpc.proto.packet.command;

/**
 * @author wangyao2221
 * @date 2020/7/31 10:32
 */
public interface Command {
    public final static byte CONNECT_REQUEST = 1;
    public final static byte CONNECT_RESPONSE = 2;
    public final static byte METHOD_CALL_REQUEST = 3;
    public final static byte METHOD_CALL_RESPONSE = 4;
}
