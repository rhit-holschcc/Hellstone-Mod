(ns com.teamclojure.ModItems
  (:require [com.teamclojure.RegistrationHelper :as helpful]))

(import com.teamclojure.hellstonemod.HellstoneSwordItem)
(import net.minecraftforge.registries.DeferredRegister)
(import net.minecraftforge.registries.ForgeRegistries)
(import net.minecraft.world.item.Item)
(import net.minecraft.world.item.ArmorItem)
(import net.minecraft.world.item.ElytraItem)
(import net.minecraft.world.item.Rarity)
(import net.minecraft.world.item.HorseArmorItem)
(import net.minecraft.world.item.TridentItem)
(import net.minecraft.world.item.SwordItem)
(import net.minecraft.world.item.ShovelItem)
(import net.minecraft.world.item.HoeItem)
(import net.minecraft.world.item.PickaxeItem)
(import net.minecraft.world.item.AxeItem)
(import net.minecraft.world.item.Item$Properties)
(import net.minecraft.world.item.CreativeModeTab)
(import net.minecraft.world.entity.EquipmentSlot)
(import net.minecraft.world.item.ArmorMaterials)
(import net.minecraft.resources.ResourceLocation)
(import com.teamclojure.hellstonemod.ModArmourMaterials)
(import net.minecraftforge.common.ForgeTier)
(import net.minecraft.tags.ItemTags)
(import net.minecraft.tags.BlockTags)
(import net.minecraft.world.item.crafting.Ingredient)

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
                                                                     (helpful/mkProperties (. CreativeModeTab TAB_MISC) [helpful/fireproof]))))
      (registerItem "hellstone_helmet" (helpful/mkSupplier (new ArmorItem (. ModArmourMaterials HELLSTONE)
                                                        (. EquipmentSlot HEAD)
                                                        (helpful/mkProperties (. CreativeModeTab TAB_COMBAT) [helpful/fireproof]))))
      (registerItem "hellstone_leggings" (helpful/mkSupplier (new ArmorItem (. ModArmourMaterials HELLSTONE)
                                                          (. EquipmentSlot LEGS)
                                                          (helpful/mkProperties (. CreativeModeTab TAB_COMBAT) [helpful/fireproof]))))
      (registerItem "hellstone_chestplate" (helpful/mkSupplier (new ArmorItem (. ModArmourMaterials HELLSTONE)
                                                            (. EquipmentSlot CHEST)
                                                            (helpful/mkProperties (. CreativeModeTab TAB_COMBAT) [helpful/fireproof]))))
      (registerItem "hellstone_boots" (helpful/mkSupplier (new ArmorItem (. ModArmourMaterials HELLSTONE)
                                                       (. EquipmentSlot FEET)
                                                       (helpful/mkProperties (. CreativeModeTab TAB_COMBAT) [helpful/fireproof]))))
      (registerItem "hellstone_sword"
                    (helpful/mkSupplier
                     (HellstoneSwordItem.
                      (ForgeTier. 2
                                  1400
                                  8.0
                                  4.0
                                  10
                                  BlockTags/NEEDS_IRON_TOOL
                                  (helpful/mkSupplier (Ingredient/of (ItemTags/IRON_ORES))))
                      2
                      3.0
                      (helpful/mkProperties (. CreativeModeTab TAB_COMBAT) [helpful/fireproof]))))
      (registerItem "hellstone_axe" (helpful/mkSupplier (new AxeItem
                                                     (ForgeTier. 2 1400 8.0 4.0 10 BlockTags/NEEDS_IRON_TOOL
                                                                 (helpful/mkSupplier (Ingredient/of (ItemTags/IRON_ORES))))
                                                     2
                                                     3
                                                     (helpful/mkProperties (. CreativeModeTab TAB_COMBAT) [helpful/fireproof]))))
      (registerItem "hellstone_pickaxe" (helpful/mkSupplier (new PickaxeItem
                                                         (ForgeTier. 2 1400 8.0 4.0 10 BlockTags/NEEDS_IRON_TOOL
                                                                     (helpful/mkSupplier (Ingredient/of (ItemTags/IRON_ORES))))
                                                         2
                                                         3
                                                         (helpful/mkProperties (. CreativeModeTab TAB_COMBAT) [helpful/fireproof]))))
      (registerItem "hellstone_hoe" (helpful/mkSupplier (new HoeItem
                                                     (ForgeTier. 2 1400 8.0 4.0 10 BlockTags/NEEDS_IRON_TOOL
                                                                 (helpful/mkSupplier (Ingredient/of (ItemTags/IRON_ORES))))
                                                     2
                                                     3
                                                     (helpful/mkProperties (. CreativeModeTab TAB_COMBAT) [helpful/fireproof]))))
      (registerItem "hellstone_shovel" (helpful/mkSupplier (new ShovelItem
                                                        (ForgeTier. 2 1400 8.0 4.0 10 BlockTags/NEEDS_IRON_TOOL
                                                                    (helpful/mkSupplier (Ingredient/of (ItemTags/IRON_ORES))))
                                                        2
                                                        3
                                                        (helpful/mkProperties (. CreativeModeTab TAB_COMBAT) [helpful/fireproof])))))))
