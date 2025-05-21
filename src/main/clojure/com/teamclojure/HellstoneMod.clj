(ns com.teamclojure.HellstoneMod)

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

;(def ITEMS nil)
;(def BLOCKS nil)
;(def HELLSTONE_BLOCK nil)
									
(defn start [eventBus]
	(def ^DeferredRegister ITEMS (DeferredRegister/create ForgeRegistries/ITEMS "hellstonemod"))
	(def ^DeferredRegister BLOCKS (DeferredRegister/create ForgeRegistries/BLOCKS "hellstonemod"))
	
	(defn itemRegister [eventBus]
		(.register ^DeferredRegister ITEMS eventBus))
	
	(defn blockRegister [eventBus]
		(.register ^DeferredRegister BLOCKS eventBus))

	(itemRegister eventBus)
	(blockRegister eventBus)

	(defn registerBlockItem [name block tab]
		(.register ^DeferredRegister ITEMS name
									   	(reify Supplier
									   			(get [_]
									   				(new BlockItem (.get ^RegistryObject block)
						  			   						   	(.tab (net.minecraft.world.item.Item$Properties.) tab))))))
	
	(defn registerBlock [name block tab]
		(let [toReturn (.register ^DeferredRegister BLOCKS name block)]
			(registerBlockItem name toReturn tab)
			(println toReturn)
			toReturn))

	(def HELLSTONE_BLOCK (registerBlock "hellstone_ore"
									(reify Supplier
										(get [_]
											(Block. (BlockBehaviour$Properties/of Material/STONE))))
									;(quote (fn [] (Block. (BlockBehaviour$Properties/of Material/STONE))))
									(. CreativeModeTab TAB_MISC))))

(defn clj-message []
	"Hello Clojure")
