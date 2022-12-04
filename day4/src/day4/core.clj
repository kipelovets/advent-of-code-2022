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

(defn pair-has-full-overlap [pair]
  (let [[elf1 elf2] (map set pair)
        overlap (set/intersection elf1 elf2)]
    (or (= overlap elf1) (= overlap elf2))))

(def bool-to-int {false 0
                  true 1})

(defn task1 [input]
  (->> input
       parse-input
       (map pair-has-full-overlap)
       (map bool-to-int)
       (reduce +)))

(defn pair-has-any-overlap [pair]
  (->> pair
       (map set)
       (apply set/intersection)
       count
       (< 0)))

(defn task2 [input]
  (->> input
       parse-input
       (map pair-has-any-overlap)
       (map bool-to-int)
       (reduce +)))
