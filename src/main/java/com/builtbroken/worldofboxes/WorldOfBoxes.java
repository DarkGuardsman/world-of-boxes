package com.builtbroken.worldofboxes;

import com.builtbroken.worldofboxes.config.ConfigDim;
import com.builtbroken.worldofboxes.network.PacketHandler;
import com.builtbroken.worldofboxes.world.BoxReplacerWorldGenerator;
import com.builtbroken.worldofboxes.world.BoxWorldProvider;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/30/2018.
 */
@Mod(modid = WorldOfBoxes.DOMAIN, name = "World of Boxes", version = WorldOfBoxes.VERSION)
@Mod.EventBusSubscriber
public class WorldOfBoxes
{
    //ID stuff
    public static final String DOMAIN = "worldofboxes";
    public static final String PREFIX = DOMAIN + ":";

    //Version stuff
    public static final String MAJOR_VERSION = "@MAJOR@";
    public static final String MINOR_VERSION = "@MINOR@";
    public static final String REVISION_VERSION = "@REVIS@";
    public static final String BUILD_VERSION = "@BUILD@";
    public static final String MC_VERSION = "@MC@";
    public static final String VERSION = MC_VERSION + "-" + MAJOR_VERSION + "." + MINOR_VERSION + "." + REVISION_VERSION + "." + BUILD_VERSION;


    @Mod.Instance(DOMAIN)
    public static WorldOfBoxes INSTANCE;

    @SidedProxy(clientSide = "com.builtbroken.worldofboxes.client.ClientProxy", serverSide = "com.builtbroken.worldofboxes.CommonProxy")
    public static CommonProxy proxy;

    public static final int ENTITY_ID_PREFIX = 50;
    private static int nextEntityID = ENTITY_ID_PREFIX;

    public static DimensionType dimensionType;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
        PacketHandler.registerMessages(DOMAIN);

        if (ConfigDim.dimID == 0)
        {
            ConfigDim.dimID = DimensionManager.getNextFreeDimId();
        }

        dimensionType = DimensionType.register(DOMAIN, "_worldofboxes", ConfigDim.dimID, BoxWorldProvider.class, true);
        DimensionManager.registerDimension(ConfigDim.dimID, dimensionType); //TODO add a pop up message to let the user change the dim ID if overlap happens

        //Generator, we want to run last to make sure we replace all blocks
        GameRegistry.registerWorldGenerator(new BoxReplacerWorldGenerator(), Integer.MAX_VALUE);
    }

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityEntry> event)
    {

    }
}
