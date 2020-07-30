package com.wangyao2221.lightrpc.proto.common;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author wangyao2221
 * @date 2020/6/26 22:09
 */
public class ReflectionUtilsTest {
    @Test
    public void newInstance() {
        ReflectionUtils.newInstance(TestClass.class);
    }

    @Test
    public void getPublicMethods() {
        Method[] methods = ReflectionUtils.getPublicMethods(TestClass.class);
        for (Method method : methods) {
            System.out.println(method.getName() + " " + Modifier.isPublic(method.getModifiers()));
        }
    }

    @Test
    public void invoke() {
        TestClass testClass = ReflectionUtils.newInstance(TestClass.class);
        Method[] methods = ReflectionUtils.getPublicMethods(TestClass.class);

        for (Method method : methods) {
            if (method.getName().equals("A")) {
                ReflectionUtils.invoke(testClass, method);
            } else if (method.getName().equals("B")) {
                ReflectionUtils.invoke(testClass, method, 1);
            }
        }
    }
}
