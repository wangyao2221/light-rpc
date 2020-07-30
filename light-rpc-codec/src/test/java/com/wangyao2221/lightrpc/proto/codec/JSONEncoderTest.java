package com.wangyao2221.lightrpc.proto.codec;

import org.junit.Test;

/**
 * @author wangyao2221
 * @date 2020/6/26 22:25
 */
public class JSONEncoderTest {

    @Test
    public void encode() {
        Encoder encoder = new JSONEncoder();
        TestBean bean = new TestBean();
        bean.setName("wangyao2221");
        bean.setAge(25);

        byte[] bytes = encoder.encode(bean);
        System.out.println(bytes == null);
    }
}