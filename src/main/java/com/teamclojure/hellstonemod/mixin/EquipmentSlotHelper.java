package com.teamclojure.hellstonemod.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class EquipmentSlotHelper {
	@Inject(at = @At("RETURN"), method = "getEquipmentSlotForItem", cancellable = true)
	private static void checkForElytra(ItemStack pItem, CallbackInfoReturnable<EquipmentSlot> cir) {
		if (pItem.getItem() instanceof ElytraItem) {
			cir.setReturnValue(EquipmentSlot.CHEST);
		}
	}
}
