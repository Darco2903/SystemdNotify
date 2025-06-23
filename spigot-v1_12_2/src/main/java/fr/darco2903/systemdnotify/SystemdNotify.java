package fr.darco2903.systemdnotify;

import fr.darco2903.logger.SpigotLogger;
import fr.darco2903.common.Main;
import org.bukkit.plugin.java.JavaPlugin;

public final class SystemdNotify extends JavaPlugin {

    @Override
    public void onEnable() {
        Main.sendNotify(new SpigotLogger(getLogger()));
    }
}
