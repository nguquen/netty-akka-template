package com.gnt.server;

import com.gnt.server.ws.WebSocketServer;
import io.netty.channel.ChannelFuture;

import java.net.InetSocketAddress;

public class MainServer {

    public static void main(String[] args) {
        // init netty
        final WebSocketServer endpoint = new WebSocketServer(Configuration.getPath());
        ChannelFuture future = endpoint.start(new InetSocketAddress(Configuration.getHost(), Configuration.getPort()));

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                endpoint.destroy();
                Akka.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }
}
