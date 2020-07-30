package com.wangyao2221.lightrpc.server;

import com.wangyao2221.lightrpc.proto.Request;
import com.wangyao2221.lightrpc.proto.ServiceDescriptor;
import com.wangyao2221.lightrpc.proto.common.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理rpc暴露的服务
 *
 * @author wangyao2221
 * @date 2020/6/26 23:32
 */
@Slf4j
public class ServiceManager {
    private Map<ServiceDescriptor, ServiceInstance>  service;

    public ServiceManager() {
        this.service = new ConcurrentHashMap<>();
    }

    public <T> void register(Class<T> interfaceClass, T bean) {
        Method[] methods = ReflectionUtils.getPublicMethods(interfaceClass);
        for (Method method : methods) {
            ServiceInstance sis = new ServiceInstance(bean, method);
            ServiceDescriptor sdp = ServiceDescriptor.from(interfaceClass, method);
            service.put(sdp, sis);
            log.info("register service: {} {}", sdp.getClazz(), sdp.getMethod());
        }
    }

    public ServiceInstance lookup(Request request) {
        ServiceDescriptor sdp = request.getService();
        return service.get(sdp);
    }
}
