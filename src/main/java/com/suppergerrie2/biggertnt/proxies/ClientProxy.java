package com.suppergerrie2.biggertnt.proxies;

import com.suppergerrie2.biggertnt.entities.EntityBiggerTNT;
import com.suppergerrie2.biggertnt.entities.rendering.RenderEntityBiggerTNT;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
    	//Here we register the renderer for our custom tnt entity
        RenderingRegistry.registerEntityRenderingHandler(EntityBiggerTNT.class, new IRenderFactory<EntityBiggerTNT>() {
            @Override
            public Render<? super EntityBiggerTNT> createRenderFor(RenderManager manager) {
                return new RenderEntityBiggerTNT(manager);
            }
        });
    }

}
