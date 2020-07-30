package com.wangyao2221.lightrpc.proto.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具类
 *
 * @author wangyao2221
 */
public class ReflectionUtils {
    /**
     * 根据class创建对象
     *
     * @param clazz 待创建对象的类
     * @param <T> 对象类型
     * @return 创建好的对象
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 获取指定类的公用方法
     *
     * @param clazz 指定类
     * @return 指定类的公用方法
     */
    public static Method[] getPublicMethods(Class clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> pMethods = new ArrayList<>();
        for (Method method : methods) {
            if (Modifier.isPublic(method.getModifiers())) {
                pMethods.add(method);
            }
        }
        return pMethods.toArray(new Method[0]);
    }

    /**
     * 获取指定类的私有方法
     *
     * @param clazz 指定类
     * @return 指定类的私有方法
     */
    public static Method[] getPrivateMethods(Class clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> pMethods = new ArrayList<>();
        for (Method method : methods) {
            if (Modifier.isPrivate(method.getModifiers())) {
                pMethods.add(method);
            }
        }
        return pMethods.toArray(new Method[0]);
    }

    /**
     * 调用指定对象的指定方法
     *
     * @param obj 被调用方法的对象
     * @param method 被调用的方法
     * @param args 被调用方法的参数
     * @return 返回结果
     */
    public static Object invoke(Object obj,
                                Method method,
                                Object... args) {
        try {
            return method.invoke(obj, args);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }
}
