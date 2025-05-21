(ns com.teamclojure.ModItems)

(import net.minecraftforge.eventbus.api.IEventBus)
(import net.minecraftforge.registries.DeferredRegister)
(import net.minecraftforge.registries.ForgeRegistries)
(import net.minecraftforge.registries.RegistryObject)
(import net.minecraft.world.item.Item)
(import net.minecraft.world.item.CreativeModeTab)
(import net.minecraft.world.level.block.state.BlockBehaviour)
(import net.minecraft.world.level.block.state.BlockBehaviour$Properties)
(import net.minecraft.world.level.material.Material)
(import java.util.function.Supplier)

(defn register [eventBus]
  (let [^DeferredRegister ITEMS (DeferredRegister/create ForgeRegistries/ITEMS "hellstonemod"),
        itemRegister (fn [eventBus]
                       (.register ^DeferredRegister ITEMS eventBus))]
    (itemRegister eventBus)
    (let [registerItem (fn [name tab]
                         (.register ^DeferredRegister ITEMS name
                                    (reify Supplier
                                      (get [_]
                                        (new Item (.tab (net.minecraft.world.item.Item$Properties.) tab))))))]
      (registerItem "hellstone_ingot" (. CreativeModeTab TAB_MISC)))))
