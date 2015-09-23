package com.gnt.server;

import akka.actor.ActorSystem;

/**
 * Created by leducthien on 9/24/15.
 */
public class Akka {
    private static final ActorSystem actorSystem;

    static {
        actorSystem = ActorSystem.create(Configuration.getAkkaSystemName(), Configuration.getConfig());
    }

    public static ActorSystem system() {
        return actorSystem;
    }

    public static void destroy() {
        actorSystem.shutdown();
    }
}
