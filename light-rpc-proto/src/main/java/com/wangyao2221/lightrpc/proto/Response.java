package com.wangyao2221.lightrpc.proto;

import lombok.Data;

/**
 * 表示RPC的返回
 *
 * @author wangyao2221
 */
@Data
public class Response {
    /**
     * 请求id，用于客户端获取结果Future
     */
    private String requestId;
    /**
     * 服务返回编码，0-成功，非0失败
     */
    private int code = 0;
    /**
     * 具体错误信息
     */
    private String message = "OK";
    /**
     * 返回的数据
     */
    private Object data;
}
