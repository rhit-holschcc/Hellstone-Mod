package com.teamclojure.hellstonemod;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HellstoneMod.MOD_ID)
public class ModWorldEvents {
	@SubscribeEvent
	public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
		HellstoneMod.require.invoke(Clojure.read("com.teamclojure.HellstoneMod"));
		IFn loadingEvent = Clojure.var("com.teamclojure.HellstoneMod", "biomeLoadingEvent");
		loadingEvent.invoke(event);
	}
}
