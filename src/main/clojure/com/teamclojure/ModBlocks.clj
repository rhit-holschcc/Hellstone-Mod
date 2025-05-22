(ns com.teamclojure.ModBlocks
  (:require [com.teamclojure.RegistrationHelper :as helpful]))

(import net.minecraftforge.eventbus.api.IEventBus)
(import net.minecraftforge.registries.DeferredRegister)
(import net.minecraftforge.registries.ForgeRegistries)
(import net.minecraftforge.registries.RegistryObject)
(import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder)
(import net.minecraft.core.Holder)
(import net.minecraft.world.item.BlockItem)
(import net.minecraft.world.item.CreativeModeTab)
(import net.minecraft.world.level.block.Block)
(import net.minecraft.world.level.block.state.BlockBehaviour)
(import net.minecraft.world.level.block.state.BlockBehaviour$Properties)
(import net.minecraft.world.level.material.Material)
(import net.minecraft.world.item.DyeColor)
(import net.minecraft.world.level.block.ShulkerBoxBlock)
(import net.minecraft.world.level.levelgen.feature.Feature)
(import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration)
(import net.minecraft.world.level.levelgen.placement.InSquarePlacement)
(import net.minecraft.world.level.levelgen.placement.BiomeFilter)
(import net.minecraft.world.level.levelgen.placement.CountPlacement)
(import net.minecraft.world.level.levelgen.placement.PlacementModifier)
(import net.minecraft.world.level.levelgen.placement.RarityFilter)
(import net.minecraft.world.level.levelgen.placement.HeightRangePlacement)
(import net.minecraft.world.level.levelgen.VerticalAnchor)
(import net.minecraft.world.level.levelgen.GenerationStep$Decoration)
(import net.minecraft.data.worldgen.features.OreFeatures)
(import net.minecraft.data.worldgen.features.FeatureUtils)
(import net.minecraft.data.worldgen.placement.PlacementUtils)
(import net.minecraftforge.event.world.BiomeLoadingEvent)
(import java.util.ArrayList)
(import java.util.List)

(def vein_size 20)
(def veins_per_chunk 100)

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
                            toReturn))
          orePlacement (fn [pModifier1 pModifier2]
                         (let [toReturn (new ArrayList)]
                           (.add toReturn pModifier1)
                           (.add toReturn (InSquarePlacement/spread))
                           (.add toReturn pModifier2)
                           (.add toReturn (BiomeFilter/biome))
                           toReturn))
          commonOrePlacement (fn [pInt pModifier]
                               (orePlacement (CountPlacement/of ^int pInt) pModifier))
          rareOrePlacement (fn [pInt pModifier]
                             (orePlacement (RarityFilter/onAverageOnceEvery pInt) pModifier))
          HELLSTONE_ORE (registerBlock "hellstone_ore"
                         (helpful/mkSupplier (Block. (BlockBehaviour$Properties/of Material/STONE)))
                                        ;(quote (fn [] (Block. (BlockBehaviour$Properties/of Material/STONE))))
                         (. CreativeModeTab TAB_MISC)
                         [helpful/fireproof])]
      (registerBlock "fireproof_shulker_box" (helpful/mkSupplier (new ShulkerBoxBlock (. DyeColor YELLOW)
                                                                      (.dynamicShape (.noOcclusion (.strength (BlockBehaviour$Properties/of Material/STONE) 2.0)))))
                     (. CreativeModeTab TAB_DECORATIONS)
                     [helpful/fireproof])
      (let [HELLSTONE_ORES_GEN (new ArrayList),
            HELLSTONE_ORE_HOLDER (. FeatureUtils register "hellstone_ore" Feature/ORE (new OreConfiguration HELLSTONE_ORES_GEN vein_size))
            HELLSTONE_ORE_PLACED (PlacementUtils/register "hellstone_ore_placed"
                                                          ^Holder HELLSTONE_ORE_HOLDER
                                                          ^ArrayList (commonOrePlacement veins_per_chunk (HeightRangePlacement/triangle (VerticalAnchor/aboveBottom -80)
                                                                                                                                        (VerticalAnchor/aboveBottom 80))))
            generateHellstoneOres (fn [event]
                                    (let [base (.getFeatures ^BiomeGenerationSettingsBuilder (.getGeneration ^BiomeLoadingEvent event) GenerationStep$Decoration/UNDERGROUND_ORES)]
                                      (.add ^List base HELLSTONE_ORE_PLACED)))]
        (defn biomeLoadingEvent [event]
          (.add ^ArrayList HELLSTONE_ORES_GEN (OreConfiguration/target OreFeatures/NETHERRACK (.defaultBlockState ^Block (.get ^RegistryObject HELLSTONE_ORE))))
          (generateHellstoneOres event))))))

