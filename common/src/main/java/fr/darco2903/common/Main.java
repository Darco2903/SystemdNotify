package fr.darco2903.common;

import fr.darco2903.logger.LoggerInterface;

public class Main {
    public static void sendNotify(LoggerInterface logger) {
        SdDaemon sdDaemon = new SdDaemon(logger);

        if (sdDaemon.booted()) {
            sdDaemon.ready();
            logger.info("Systemd notification sent!");
        } else {
            logger.info("Systemd not found, skipping notification.");
        }
    }
}
