(ns com.teamclojure.ModItems)

(import net.minecraftforge.registries.DeferredRegister)
(import net.minecraftforge.registries.ForgeRegistries)
(import net.minecraft.world.item.Item)
(import net.minecraft.world.item.Item$Properties)
(import net.minecraft.world.item.CreativeModeTab)
(import java.util.function.Supplier)

(defn register [eventBus modId]
  (let [^DeferredRegister ITEMS (DeferredRegister/create ForgeRegistries/ITEMS ^String modId),
        itemRegister (fn [eventBus]
                       (.register ^DeferredRegister ITEMS eventBus))]
    (itemRegister eventBus)
    ; funs should be a list of functions that take an Item.Properties & return an Item.Properties
    (let [registerItem (fn [name tab funs]
                         (.register ^DeferredRegister ITEMS name
                                    (reify Supplier
                                      (get [_]
                                        (new Item (reduce (fn [prev fun] (fun prev))
                                                          (.tab (net.minecraft.world.item.Item$Properties.) tab)
                                                          funs)))))),
          fireproof (fn [properties] (.fireResistant ^Item$Properties properties))]
      (dorun (map (fn [name] (registerItem name (. CreativeModeTab TAB_MATERIALS) [fireproof]))
                  ["raw_hellstone" "hellstone_ingot" "fireproof_plating"])))))
