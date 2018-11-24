package com.suppergerrie2.biggertnt;

import org.apache.logging.log4j.Logger;

import com.suppergerrie2.biggertnt.entities.EntityBiggerTNT;
import com.suppergerrie2.biggertnt.proxies.IProxy;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

@Mod(modid=Reference.MODID, version=Reference.VERSION, name=Reference.MODNAME, acceptedMinecraftVersions=Reference.ACCEPTED_MINECRAFT_VERSIONS)
@Mod.EventBusSubscriber(modid=Reference.MODID)
public class Main {

	@Instance
	public static Main instance;

	public static Logger logger;
	
    @SidedProxy(
            clientSide = "com.suppergerrie2.biggertnt.proxies.ClientProxy",
            serverSide = "com.suppergerrie2.biggertnt.proxies.ServerProxy"
    )
    public static IProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		logger.info("preInit");
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		logger.info("init");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		logger.info("postInit");
	}

	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
		//Register the custom tnt entity
		 EntityEntry biggerTNT = EntityEntryBuilder.create().entity(EntityBiggerTNT.class)
	                .name(Reference.MODID + ":bigger_tnt")
	                .tracker(80, 1, false)
	                .id(new ResourceLocation(Reference.MODID,"bigger_tnt"), 0).build();
        event.getRegistry().register(biggerTNT);
	}

}
