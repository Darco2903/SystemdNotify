package fr.darco2903.systemdnotify;

import fr.darco2903.common.Main;
import fr.darco2903.logger.SpigotLogger;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.util.logging.Logger;

import static fr.darco2903.common.Config.MODID;
import static fr.darco2903.common.Config.MODNAME;


@Mod(modid = MODID)
@Mod.EventBusSubscriber(modid = MODID)
public class SystemdNotify {
    //    public static final Logger LOGGER = LoggerFactory.getLogger(MODNAME);
    public static final Logger LOGGER = Logger.getLogger(MODNAME);

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        Main.sendNotify(new SpigotLogger(LOGGER));
    }
}