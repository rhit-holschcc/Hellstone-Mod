(ns com.teamclojure.RegistrationHelper)
(import java.util.function.Supplier)
(import net.minecraft.world.item.Item$Properties)

(defmacro mkSupplier
  [item]
  `(reify Supplier (get [_] ~item)))

(defn mkProperties [tab funs] (reduce (fn [prev fun] (fun prev))
                                      (.tab (net.minecraft.world.item.Item$Properties.) tab)
                                      funs))

(defn fireproof [properties] (.fireResistant ^Item$Properties properties))
