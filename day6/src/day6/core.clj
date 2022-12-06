(ns day6.core
  (:require [clojure.string :as str]))

(def marker-len 4)

(defn find-marker [buffer]
  (loop [ind 0]
    (if (> ind (+ (count buffer) marker-len))
      nil
      (let [chars (subs buffer ind (+ ind marker-len))]
        (if (= marker-len (count (set chars)))
          (+ marker-len ind)
          (recur (inc ind)))))))