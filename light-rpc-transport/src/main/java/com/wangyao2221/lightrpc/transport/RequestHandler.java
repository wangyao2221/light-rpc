package com.wangyao2221.lightrpc.transport;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 处理网络请求handler
 *
 * @author wangyao2221
 * @date 2020/6/26 22:56
 */
public interface RequestHandler {
    void onRequest(InputStream receive, OutputStream to);
}
