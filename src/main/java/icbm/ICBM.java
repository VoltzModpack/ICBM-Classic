package icbm;

import icbm.client.ClientProxy;
import icbm.config.ConfigHolder;
import icbm.event.ICBMEvents;
import icbm.init.CommonProxy;
import icbm.init.ICBMBlocks;
import icbm.init.ICBMContainers;
import icbm.init.ICBMEntities;
import icbm.init.ICBMItems;
import icbm.init.ICBMTiles;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ICBM.ID)
@Mod.EventBusSubscriber
public final class ICBM {

	public static final String ID = "icbm_classic";

	public static CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

	protected static Logger logger = LogManager.getLogger(ICBM.ID);

//	public static final ItemGroup CREATIVE_TAB = new ItemGroup(ICBM.ID){}

	public ICBM() {
		MinecraftForge.EVENT_BUS.register(this);

		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		modEventBus.addListener(this::onClientSetupEvent);
		modEventBus.addListener(this::onCommonSetupEvent);
		MinecraftForge.EVENT_BUS.register(ICBMEvents.class);

		ICBMBlocks.BLOCKS.register(modEventBus);
		ICBMContainers.CONTAINERS.register(modEventBus);
		ICBMEntities.ENTITIES.register(modEventBus);
		ICBMItems.ITEMS.register(modEventBus);
		ICBMTiles.TILES.register(modEventBus);

		ModLoadingContext modLoadingContext = ModLoadingContext.get();
		modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigHolder.getClientSpec());
		modLoadingContext.registerConfig(ModConfig.Type.SERVER, ConfigHolder.getServerSpec());
	}

	public void onClientSetupEvent(final FMLClientSetupEvent event) {
	}

	public void onCommonSetupEvent(final FMLCommonSetupEvent event) {
	}

}
