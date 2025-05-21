(ns com.teamclojure.HellstoneMod
  (:require [com.teamclojure.ModBlocks :as blocks]))

(defn start [eventBus]
  (blocks/register eventBus))

(defn clj-message []
	"Hello Clojure")
