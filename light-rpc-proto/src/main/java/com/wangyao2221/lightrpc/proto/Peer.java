package com.wangyao2221.lightrpc.proto;

import lombok.Data;
import lombok.AllArgsConstructor;

/**
 * 表示网络传输的一个端点
 *
 * @author wangyao2221
 */
@Data
@AllArgsConstructor
public class Peer {
    private String host;
    private int port;
}
