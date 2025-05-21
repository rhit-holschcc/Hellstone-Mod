(ns com.teamclojure.HellstoneMod
  (:require [com.teamclojure.ModBlocks :as blocks])
  (:require [com.teamclojure.ModItems :as items]))

(defn start [eventBus]
  (blocks/register eventBus)
  (items/register eventBus))

(defn clj-message []
	"Hello Clojure")
