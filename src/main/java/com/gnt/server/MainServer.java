package com.gnt.server;

import com.gnt.server.ws.WebSocketServer;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class MainServer {

    public static void main(String[] args) {
        // init akka
        Akka.init();
        // init netty
        final WebSocketServer endpoint = new WebSocketServer(Configuration.getPath());
        ChannelFuture future = endpoint.start(new InetSocketAddress(Configuration.getHost(), Configuration.getPort()));
        // logger
        final Logger logger = LoggerFactory.getLogger(MainServer.class);
        // shutdownhook
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                endpoint.destroy();
                Akka.destroy();
                logger.info("server exited!");
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }
}
