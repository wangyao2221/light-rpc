package com.wangyao2221.lightrpc.proto;

import lombok.Data;

/**
 *  表示PRC的一个请求
 *
 * @author wangyao2221
 */

@Data
public class Request {
    /**
     * 请求id，用于客户端获取结果Future
     */
    private String requestId;
    private ServiceDescriptor service;
    private Object[] parameters;
}
