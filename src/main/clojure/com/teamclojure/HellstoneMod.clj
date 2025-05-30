(ns com.teamclojure.HellstoneMod
  (:require [com.teamclojure.ModBlocks :as blocks])
  (:require [com.teamclojure.ModItems :as items]))

(defn start [eventBus modId]
  (blocks/register eventBus modId)
  (items/register eventBus modId))
  
(defn biomeLoadingEvent [event]
	(blocks/biomeLoadingEvent event))

(defn clj-message []
	"Hello Clojure")
