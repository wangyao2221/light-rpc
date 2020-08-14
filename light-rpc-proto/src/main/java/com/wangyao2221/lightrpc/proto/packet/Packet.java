package com.wangyao2221.lightrpc.proto.packet;

import lombok.Data;

/**
 * @author wangyao2221
 * @date 2020/7/30 23:02
 */
@Data
public abstract class Packet {
    /**
     * 协议版本号
     */
    private Byte version = 1;

    /**
     * 指令
     * @return
     */
    public abstract Byte getCommand();
}
