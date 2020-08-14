package com.wangyao2221.lightrpc.proto.codec;

import com.wangyao2221.lightrpc.proto.ProtocolConstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangyao2221
 * @date 2020/8/2 22:59
 */
@Slf4j
public class Spliter extends LengthFieldBasedFrameDecoder {
    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int FIELD_LENGTH = 4;

    public Spliter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 通过魔数判断是否是当前协议的消息
        if (in.getInt(in.readerIndex()) != ProtocolConstant.MAGIC_NUMBER) {
            ctx.channel().close();
            log.info("MAGIC_NUMBER not match");
            return null;
        }

        log.info("MAGIC_NUMBER match");

        return super.decode(ctx, in);
    }
}
