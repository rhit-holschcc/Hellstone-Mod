(ns com.teamclojure.ModItems)

(import net.minecraftforge.registries.DeferredRegister)
(import net.minecraftforge.registries.ForgeRegistries)
(import net.minecraft.world.item.Item)
(import net.minecraft.world.item.ArmorItem)
(import net.minecraft.world.item.Item$Properties)
(import net.minecraft.world.item.CreativeModeTab)
(import net.minecraft.world.entity.EquipmentSlot)
(import net.minecraft.world.item.ArmorMaterials)
(import java.util.function.Supplier)

(defmacro mkSupplier
  [item]
  `(reify Supplier (get [_] ~item)))

(defn register [eventBus modId]
  (let [^DeferredRegister ITEMS (DeferredRegister/create ForgeRegistries/ITEMS ^String modId),
        itemRegister (fn [eventBus]
                       (.register ^DeferredRegister ITEMS eventBus))]
    (itemRegister eventBus)
    ; funs should be a list of functions that take an Item.Properties & return an Item.Properties
    (let [mkProperties (fn [tab funs] (reduce (fn [prev fun] (fun prev))
                                              (.tab (net.minecraft.world.item.Item$Properties.) tab)
                                              funs)),
          registerItem (fn [name supplier]
                         (.register ^DeferredRegister ITEMS name supplier)),
          fireproof (fn [properties] (.fireResistant ^Item$Properties properties))]
      (dorun (map (fn [name] (registerItem name (mkSupplier (new Item (mkProperties (. CreativeModeTab TAB_MATERIALS) [fireproof])))))
                  ["raw_hellstone" "hellstone_ingot" "fireproof_plating"]))
      (registerItem "fireproof_turtle_helmet" (mkSupplier (new ArmorItem (. ArmorMaterials TURTLE)
                                                               (. EquipmentSlot HEAD)
                                                               (mkProperties (. CreativeModeTab TAB_COMBAT) [fireproof])))))))
