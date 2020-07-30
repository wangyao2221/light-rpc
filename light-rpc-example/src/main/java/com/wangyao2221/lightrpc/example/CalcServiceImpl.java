package com.wangyao2221.lightrpc.example;

/**
 * @author wangyao2221
 * @date 2020/6/27 09:44
 */
public class CalcServiceImpl implements CalcService {
    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int minus(int a, int b) {
        return a - b;
    }
}
