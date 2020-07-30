package com.wangyao2221.lightrpc.proto.codec;

import org.junit.Test;

/**
 * @author wangyao2221
 * @date 2020/6/26 22:24
 */
public class JSONDecoderTest {

    @Test
    public void decode() {
        Encoder encoder = new JSONEncoder();
        TestBean bean = new TestBean();
        bean.setName("wangyao2221");
        bean.setAge(25);
        byte[] bytes = encoder.encode(bean);

        Decoder decoder = new JSONDecoder();
        TestBean decodeBean = decoder.decode(bytes, TestBean.class);
        System.out.println(decodeBean.toString());
    }
}