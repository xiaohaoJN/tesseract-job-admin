package tesseract.core.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import tesseract.exception.TesseractException;

@Slf4j
@Data
public class NettyClient {
    private EventLoopGroup eventLoopGroup;
    private ChannelInboundHandlerAdapter handlerAdapter;
    private String host;
    private int port;
    private Channel channel;

    public NettyClient(String host, int port, ChannelInboundHandlerAdapter handlerAdapter) {
        this.host = host;
        this.port = port;
        this.handlerAdapter = handlerAdapter;
    }

    public void close() {
        if (channel.isActive()) {
            channel.close();
        }
        if (eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public Channel getActiveChannel() {
        if (channel == null || !channel.isActive()) {
            try {
                this.connect();
            } catch (Exception e) {
                e.printStackTrace();
                throw new TesseractException("初始化channel出错");
            }
        }
        return this.channel;
    }

    private void connect() throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new HttpResponseDecoder());
                ch.pipeline().addLast(new HttpRequestEncoder());
                ch.pipeline().addLast(new HttpObjectAggregator(5 * 1024));
                ch.pipeline().addLast(handlerAdapter);
            }
        });
        ChannelFuture f = b.connect(host, port).sync();
        this.channel = f.channel();
    }

}