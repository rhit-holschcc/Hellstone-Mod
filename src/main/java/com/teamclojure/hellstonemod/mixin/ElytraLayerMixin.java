package com.teamclojure.hellstonemod.mixin;

import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ElytraLayer.class)
public class ElytraLayerMixin {
	@Inject(at = @At("RETURN"), method = "shouldRender", cancellable = true, remap = false)
	private void shouldRender(ItemStack stack, LivingEntity entity, CallbackInfoReturnable<Boolean> c) {
		c.setReturnValue(stack.getItem() instanceof ElytraItem);
	}
}
