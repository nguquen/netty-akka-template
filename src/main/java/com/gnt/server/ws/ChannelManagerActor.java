package com.gnt.server.ws;

import akka.actor.UntypedActor;
import com.gnt.server.ws.message.AddChannelMsg;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.ImmediateEventExecutor;

/**
 * Created by leducthien on 9/24/15.
 */
public class ChannelManagerActor extends UntypedActor {
    @Override
    public void preStart() throws Exception {
        super.preStart();
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        channelGroup.close();
    }

    private final ChannelGroup channelGroup;

    public ChannelManagerActor() {
        channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof AddChannelMsg) {
            channelGroup.add(((AddChannelMsg) message).getChannel());
        } else {
            unhandled(message);
        }
    }
}
