package com.gnt.server.ws.message;

import io.netty.channel.Channel;

import java.io.Serializable;

/**
 * Created by leducthien on 9/24/15.
 */
public class AddChannelMsg implements Serializable {
    private final Channel channel;

    public AddChannelMsg(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return this.channel;
    }
}
