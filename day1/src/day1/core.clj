(ns day1.core
  (:require [clojure.string :as str]))

(defn read-calories-per-elf [data]
  (let [lines (str/split-lines data)]
    (reduce (fn [acc line]
              (if (= "" line)
                (conj acc 0)
                (conj (vec (drop-last acc)) (+ (last acc)
                                               (Integer/parseInt line)))))
            [0]
            lines)))

(defn fattest-elf [data]
  (apply max data))

(defn top-three [data]
  (reduce + (subvec (vec (sort > data)) 0 3)))