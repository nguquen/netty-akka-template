package com.gnt.server.ws;

import akka.actor.ActorRef;
import akka.actor.Props;
import com.gnt.server.Akka;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class WebSocketServer {
    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel;
    private final String path;

    public WebSocketServer(String path) {
        this.path = path;
    }

    public ChannelFuture start(InetSocketAddress address) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(createInitializer(path));
        ChannelFuture future = bootstrap.bind(address);
        future.syncUninterruptibly();
        logger.info("server started at: " + address.getHostName() + ":" + address.getPort());
        channel = future.channel();
        return future;
    }

    private ChannelInitializer<Channel> createInitializer(String path) {
        return new WebSocketServerInitializer(path);
    }

    public void destroy() {
        if (channel != null) {
            channel.close();
        }
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}
