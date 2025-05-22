(ns com.teamclojure.ModItems
  (:require [com.teamclojure.RegistrationHelper :as helpful]))

(import net.minecraftforge.registries.DeferredRegister)
(import net.minecraftforge.registries.ForgeRegistries)
(import net.minecraft.world.item.Item)
(import net.minecraft.world.item.ArmorItem)
(import net.minecraft.world.item.ElytraItem)
(import net.minecraft.world.item.Rarity)
(import net.minecraft.world.item.HorseArmorItem)
(import net.minecraft.world.item.TridentItem)
(import net.minecraft.world.item.Item$Properties)
(import net.minecraft.world.item.CreativeModeTab)
(import net.minecraft.world.entity.EquipmentSlot)
(import net.minecraft.world.item.ArmorMaterials)
(import net.minecraft.resources.ResourceLocation)

(defn register [eventBus modId]
  (let [^DeferredRegister ITEMS (DeferredRegister/create ForgeRegistries/ITEMS ^String modId)]
    (.register ^DeferredRegister ITEMS eventBus)
    (let [registerItem (fn [name supplier]
                         (.register ^DeferredRegister ITEMS name supplier)),
          durability (fn [amount] (fn [properties] (.durability ^Item$Properties properties amount)))]
      (dorun (map (fn [name] (registerItem name (helpful/mkSupplier (new Item (helpful/mkProperties (. CreativeModeTab TAB_MATERIALS) [helpful/fireproof])))))
                  ["raw_hellstone" "hellstone_ingot" "fireproof_plating"]))
      (registerItem "fireproof_turtle_helmet" (helpful/mkSupplier (new ArmorItem (. ArmorMaterials TURTLE)
                                                                       (. EquipmentSlot HEAD)
                                                                       (helpful/mkProperties (. CreativeModeTab TAB_COMBAT) [helpful/fireproof]))))
      (registerItem "fireproof_trident" (helpful/mkSupplier (new TridentItem (helpful/mkProperties (. CreativeModeTab TAB_COMBAT)
                                                                                                   [helpful/fireproof
                                                                                                    (durability 250)]))))
      (registerItem "fireproof_elytra" (helpful/mkSupplier (new ElytraItem (helpful/mkProperties (. CreativeModeTab TAB_TRANSPORTATION)
                                                                                                 [helpful/fireproof
                                                                                                  (durability 432)
                                                                                                  (fn [properties] (.rarity ^Item$Properties properties (. Rarity UNCOMMON)))]))))
      (registerItem "fireproof_horse_armor" (helpful/mkSupplier (new HorseArmorItem 11
                                                                     (new ResourceLocation modId "textures/entity/horse/armor/horse_armor_fireproof.png")
                                                                     (helpful/mkProperties (. CreativeModeTab TAB_MISC) [helpful/fireproof])))))))
