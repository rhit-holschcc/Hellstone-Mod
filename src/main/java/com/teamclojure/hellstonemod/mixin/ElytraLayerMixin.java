package com.teamclojure.hellstonemod.mixin;

import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.teamclojure.hellstonemod.HellstoneMod;

@Mixin(ElytraLayer.class)
public class ElytraLayerMixin {
	private static final ResourceLocation FIREPROOF_WINGS_LOCATION = new ResourceLocation(HellstoneMod.MOD_ID,
			"textures/entity/fireproof_elytra.png");

	@Inject(at = @At("RETURN"), method = "shouldRender", cancellable = true, remap = false)
	private void shouldRender(ItemStack stack, LivingEntity entity, CallbackInfoReturnable<Boolean> c) {
		c.setReturnValue(stack.getItem() instanceof ElytraItem);
	}

	@Inject(at = @At("RETURN"), method = "getElytraTexture", cancellable = true, remap = false)
	private void getElytraTexture(ItemStack stack, LivingEntity entity,
			CallbackInfoReturnable<ResourceLocation> c) {
		if (stack.getItem() != Items.ELYTRA) {
			c.setReturnValue(FIREPROOF_WINGS_LOCATION);
		}
	}
}
