package com.gnt.server;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by leducthien on 9/23/15.
 */
public class Configuration {
    private static final Config config;
    static {
        System.setProperty("logback.configurationFile", "conf/logback.xml");
        System.setProperty("config.file", "conf/application.conf");
        config = ConfigFactory.load();
    }

    public static String getHost() {
        return config.getString("netty.ws.hostname");
    }

    public static int getPort() {
        return config.getInt("netty.ws.port");
    }

    public static String getPath() {
        return config.getString("netty.ws.path");
    }

    public static String getDefaultMessage() {
        return config.getString("netty.http.default-message");
    }

    public static int getMaxContentLength() {
        return config.getInt("netty.http.max-content-length");
    }

    public static String getAkkaSystemName() {
        return config.getString("akka.system-name");
    }

    public static Config getConfig() {
        return config;
    }
}
