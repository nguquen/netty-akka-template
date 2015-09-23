package com.gnt.server.ws;

import akka.actor.ActorRef;
import com.gnt.server.Configuration;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServerInitializer extends ChannelInitializer<Channel> {
    private final String path;
    private final ActorRef channelManagerActor;

    public WebSocketServerInitializer(ActorRef channelManagerActor, String path) {
        this.path = path;
        this.channelManagerActor = channelManagerActor;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(Configuration.getMaxContentLength()));
        pipeline.addLast(new DefaultHttpRequestHandler(this.path));
        pipeline.addLast(new WebSocketServerProtocolHandler(this.path));
        pipeline.addLast(new TextWebSocketFrameHandler(channelManagerActor));
    }
}
