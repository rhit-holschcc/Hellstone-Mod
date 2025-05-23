(ns com.teamclojure.ModItems)

(import com.teamclojure.hellstonemod.HellstoneSwordItem)
(import net.minecraftforge.registries.DeferredRegister)
(import net.minecraftforge.registries.ForgeRegistries)
(import net.minecraft.world.item.Item)
(import net.minecraft.world.item.ArmorItem)
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
(import com.teamclojure.hellstonemod.ModArmourMaterials)
(import java.util.function.Supplier)
(import net.minecraftforge.common.ForgeTier)
(import net.minecraft.tags.ItemTags)
(import net.minecraft.tags.BlockTags)
(import net.minecraft.world.item.crafting.Ingredient)

(defmacro mkSupplier
  [item]
  `(reify Supplier (get [_] ~item)))

(def mkProperties  (fn [tab funs] (reduce (fn [prev fun] (fun prev))
                                          (.tab (net.minecraft.world.item.Item$Properties.) tab)
                                          funs)))

(defn register [eventBus modId]
  (let [^DeferredRegister ITEMS (DeferredRegister/create ForgeRegistries/ITEMS ^String modId),
        itemRegister (fn [eventBus]
                       (.register ^DeferredRegister ITEMS eventBus))]
    (itemRegister eventBus)
    ; funs should be a list of functions that take an Item.Properties & return an Item.Properties
    (let [registerItem (fn [name supplier]
                         (.register ^DeferredRegister ITEMS name supplier)),
          fireproof (fn [properties] (.fireResistant ^Item$Properties properties))]
      (dorun (map (fn [name] (registerItem name (mkSupplier (new Item (mkProperties (. CreativeModeTab TAB_MATERIALS) [fireproof])))))
                  ["raw_hellstone" "hellstone_ingot" "fireproof_plating"]))
      (registerItem "fireproof_turtle_helmet" (mkSupplier (new ArmorItem (. ArmorMaterials TURTLE)
                                                               (. EquipmentSlot HEAD)
                                                               (mkProperties (. CreativeModeTab TAB_COMBAT) [fireproof]))))
      (registerItem "fireproof_trident" (mkSupplier (new TridentItem (mkProperties (. CreativeModeTab TAB_COMBAT)
                                                                                   [fireproof
                                                                                    (fn [properties] (.durability ^Item$Properties properties 250))]))))
      (registerItem "hellstone_helmet" (mkSupplier (new ArmorItem (. ModArmourMaterials HELLSTONE)
                                                     (. EquipmentSlot HEAD)
                                                     (mkProperties (. CreativeModeTab TAB_COMBAT) [fireproof])
                                                     )))
      (registerItem "hellstone_leggings" (mkSupplier (new ArmorItem (. ModArmourMaterials HELLSTONE)
                                                     (. EquipmentSlot LEGS)
                                                     (mkProperties (. CreativeModeTab TAB_COMBAT) [fireproof])
                                                     )))
      (registerItem "hellstone_chestplate" (mkSupplier (new ArmorItem (. ModArmourMaterials HELLSTONE)
                                                     (. EquipmentSlot CHEST)
                                                     (mkProperties (. CreativeModeTab TAB_COMBAT) [fireproof])
                                                     )))
      (registerItem "hellstone_boots" (mkSupplier (new ArmorItem (. ModArmourMaterials HELLSTONE)
                                                     (. EquipmentSlot FEET)
                                                     (mkProperties (. CreativeModeTab TAB_COMBAT) [fireproof])
                                                     )))
      (registerItem "hellstone_sword"
                    (mkSupplier
                     (HellstoneSwordItem.
                       (ForgeTier. 2
                                   1400
                                   8.0
                                   4.0
                                   10
                                   BlockTags/NEEDS_IRON_TOOL
                                   (mkSupplier (Ingredient/of (ItemTags/IRON_ORES))))
                       2
                       3.0
                       (mkProperties (. CreativeModeTab TAB_COMBAT) [fireproof]))))
      (registerItem "hellstone_axe" (mkSupplier (new AxeItem
                                                    (ForgeTier. 2 1400 8.0 4.0 10 BlockTags/NEEDS_IRON_TOOL
                                                                (mkSupplier (Ingredient/of (ItemTags/IRON_ORES))))
                                                    2
                                                    3
                                                    (mkProperties (. CreativeModeTab TAB_COMBAT) [fireproof])
                                                    )))
      (registerItem "hellstone_pickaxe" (mkSupplier (new PickaxeItem
                                                    (ForgeTier. 2 1400 8.0 4.0 10 BlockTags/NEEDS_IRON_TOOL
                                                                (mkSupplier (Ingredient/of (ItemTags/IRON_ORES))))
                                                    2
                                                    3
                                                    (mkProperties (. CreativeModeTab TAB_COMBAT) [fireproof])
                                                    )))
      (registerItem "hellstone_hoe" (mkSupplier (new HoeItem
                                                    (ForgeTier. 2 1400 8.0 4.0 10 BlockTags/NEEDS_IRON_TOOL
                                                                (mkSupplier (Ingredient/of (ItemTags/IRON_ORES))))
                                                    2
                                                    3
                                                    (mkProperties (. CreativeModeTab TAB_COMBAT) [fireproof])
                                                    )))
      (registerItem "hellstone_shovel" (mkSupplier (new ShovelItem
                                                    (ForgeTier. 2 1400 8.0 4.0 10 BlockTags/NEEDS_IRON_TOOL
                                                                (mkSupplier (Ingredient/of (ItemTags/IRON_ORES))))
                                                    2
                                                    3
                                                    (mkProperties (. CreativeModeTab TAB_COMBAT) [fireproof])
                                                    ))))))