package com.wangyao2221.lightrpc.server;

import com.wangyao2221.lightrpc.proto.Request;
import com.wangyao2221.lightrpc.proto.common.ReflectionUtils;

/**
 * @author wangyao2221
 * @date 2020/6/27 00:05
 */
public class ServiceInvoker {
    public Object invoke(ServiceInstance service, Request request) {
        return ReflectionUtils.invoke(
                service.getTarget(),
                service.getMethod(),
                request.getParameters());
    }
}
