package admin.core.netty.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: nickle
 * @create: 2019-09-25 16:14
 **/
@ChannelHandler.Sharable
@Slf4j
@AllArgsConstructor
public class TesseractTaskExecutorHandler extends ChannelInboundHandlerAdapter {
    private String socket;
    private Integer executorDetailId;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.toString());
    }
}
