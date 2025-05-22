(ns com.teamclojure.ModBlocks)

(import net.minecraftforge.eventbus.api.IEventBus)
(import net.minecraftforge.registries.DeferredRegister)
(import net.minecraftforge.registries.ForgeRegistries)
(import net.minecraftforge.registries.RegistryObject)
(import net.minecraft.world.level.block.Block)
(import net.minecraft.world.item.BlockItem)
(import net.minecraft.world.item.CreativeModeTab)
(import net.minecraft.world.level.block.state.BlockBehaviour)
(import net.minecraft.world.level.block.state.BlockBehaviour$Properties)
(import net.minecraft.world.level.material.Material)
(import java.util.function.Supplier)



(defn register [eventBus modId]
  (let [^DeferredRegister ITEMS (DeferredRegister/create ForgeRegistries/ITEMS ^String modId),
        ^DeferredRegister BLOCKS (DeferredRegister/create ForgeRegistries/BLOCKS ^String modId),
        itemRegister (fn [eventBus]
                       (.register ^DeferredRegister ITEMS eventBus)),
        blockRegister (fn [eventBus]
                        (.register ^DeferredRegister BLOCKS eventBus))]

    (itemRegister eventBus)
    (blockRegister eventBus)

    (let [registerBlockItem (fn [name block tab]
                              (.register ^DeferredRegister ITEMS name
                                         (reify Supplier
                                           (get [_]
                                             (new BlockItem (.get ^RegistryObject block)
                                                  (.tab (net.minecraft.world.item.Item$Properties.) tab)))))),
          registerBlock (fn [name block tab]
                          (let [toReturn (.register ^DeferredRegister BLOCKS name block)]
                            (registerBlockItem name toReturn tab)
                            (println toReturn)
                            toReturn))
          HELLSTONE_ORE (registerBlock "hellstone_ore"
                         (reify Supplier
                                (get [_]
                                     (Block. (BlockBehaviour$Properties/of Material/STONE))))
                         ;(quote (fn [] (Block. (BlockBehaviour$Properties/of Material/STONE))))
                         (. CreativeModeTab TAB_MISC))
          HELLSTONE_BLOCK (registerBlock "hellstone_block"
                                         (reify Supplier
                                                (get [_]
                                                     (Block. (.. BlockBehaviour$Properties
                                                                 (of Material/METAL)
                                                                 (strength 3.0 3.0)
                                                                 )
                                                             )))
                                         ;(quote (fn [] (Block. (BlockBehaviour$Properties/of Material/STONE))))
                                         (. CreativeModeTab TAB_MISC))
          ]


      )

    )
  )






