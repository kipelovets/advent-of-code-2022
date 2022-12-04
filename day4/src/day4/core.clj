(ns day4.core
  (:require [clojure.set :as set]
            [clojure.string :as str]))

(defn parse-input [input]
  (->> input
       (str/split-lines)
       (map #(str/split % #","))
       (map (fn [line]
              (map #(let [[begin end] (str/split % #"\-")]
                      (range (Integer/parseInt begin)
                             (inc (Integer/parseInt end))))
                   line)))))

(defn pair-has-overlap [pair]
  (let [[elf1 elf2] (map set pair)
        overlap (set/intersection elf1 elf2)]
    (or (= overlap elf1) (= overlap elf2))))

(def bool-to-int {false 0
                  true 1})

(defn task1 [input]
  (->> input
       parse-input
       (map pair-has-overlap)
       (map bool-to-int)
       (reduce +)))