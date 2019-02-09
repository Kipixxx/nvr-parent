package fr.novaria.test;

import org.bukkit.Server;

import java.util.logging.Logger;

public abstract class AbstractServer implements Server {

    private final Logger logger = Logger.getLogger(AbstractServer.class.getName());

    @Override
    public String getName() {
        return "server";
    }

    @Override
    public String getVersion() {
        return "0.0.1";
    }

    @Override
    public String getBukkitVersion() {
        return "0.0.2";
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
