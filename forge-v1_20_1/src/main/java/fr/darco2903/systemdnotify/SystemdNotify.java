package fr.darco2903.systemdnotify;

import fr.darco2903.logger.ForgeLogger;
import fr.darco2903.common.Main;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static fr.darco2903.common.Config.MODID;
import static fr.darco2903.common.Config.MODNAME;

@Mod(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SystemdNotify {
    public static final Logger LOGGER = LoggerFactory.getLogger(MODNAME);

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        Main.sendNotify(new ForgeLogger(LOGGER));
    }
}
