(ns com.teamclojure.ModBlocks
  (:require [com.teamclojure.RegistrationHelper :as helpful]))

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
(import net.minecraft.world.item.DyeColor)
(import net.minecraft.world.level.block.ShulkerBoxBlock)

(defn register [eventBus modId]
  (let [^DeferredRegister ITEMS (DeferredRegister/create ForgeRegistries/ITEMS ^String modId),
        ^DeferredRegister BLOCKS (DeferredRegister/create ForgeRegistries/BLOCKS ^String modId),
        itemRegister (fn [eventBus]
                       (.register ^DeferredRegister ITEMS eventBus)),
        blockRegister (fn [eventBus]
                        (.register ^DeferredRegister BLOCKS eventBus))]

    (itemRegister eventBus)
    (blockRegister eventBus)

    (let [registerBlockItem (fn [name block tab funs]
                              (.register ^DeferredRegister ITEMS name
                                         (helpful/mkSupplier (new BlockItem (.get ^RegistryObject block)
                                                                  (helpful/mkProperties tab funs))))),
          registerBlock (fn [name block tab funs]
                          (let [toReturn (.register ^DeferredRegister BLOCKS name block)]
                            (registerBlockItem name toReturn tab funs)
                            (println toReturn)
                            toReturn))]
      (registerBlock "hellstone_ore"
                     (helpful/mkSupplier (Block. (BlockBehaviour$Properties/of Material/STONE)))
                                                             ;(quote (fn [] (Block. (BlockBehaviour$Properties/of Material/STONE))))
                     (. CreativeModeTab TAB_MISC)
                     [helpful/fireproof])
      (registerBlock "fireproof_shulker_box" (helpful/mkSupplier (new ShulkerBoxBlock (. DyeColor YELLOW)
                                                                      (.dynamicShape (.noOcclusion (.strength (BlockBehaviour$Properties/of Material/STONE) 2.0)))))
                     (. CreativeModeTab TAB_DECORATIONS)
                     [helpful/fireproof]))))
