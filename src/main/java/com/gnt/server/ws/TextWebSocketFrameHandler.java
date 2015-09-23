package com.gnt.server.ws;

import akka.actor.ActorRef;
import com.gnt.server.ws.message.AddChannelMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private final Logger logger = LoggerFactory.getLogger(TextWebSocketFrameHandler.class);
    private final ActorRef channelManagerActor;

    public TextWebSocketFrameHandler(ActorRef channelManagerActor) {
        this.channelManagerActor = channelManagerActor;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            ctx.pipeline().remove(DefaultHttpRequestHandler.class);
            channelManagerActor.tell(new AddChannelMsg(ctx.channel()), ActorRef.noSender());
            logger.info("client " + ctx.channel().hashCode() + " joined");
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //group.writeAndFlush(msg.retain());
    }
}
