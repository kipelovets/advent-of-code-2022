(ns day3.core
  (:require [clojure.set :as set]))

(defn illegal-items [rucksack]
  (let [[part1 part2] (split-at (/ (count rucksack) 2) rucksack)]
    (set/intersection (set part1) (set part2))))

(defn priority [item]
  (let [char (int item)
        a (int \a)
        A (- (int \A) 26)
        base (if (re-find #"[a-z]" (str item))
               a
               A)]
    (inc (- char base))))

(defn task1 [rucksacks]
  (->> rucksacks
       (map illegal-items)
       (reduce concat)
       (map priority)
       (reduce +)))

(defn group-badge [group]
  (->> group
       (map set)
       (apply set/intersection)))

(defn task2 [rucksacks]
  (->> rucksacks
       (partition 3)
       (map group-badge)
       (reduce concat)
       (map priority)
       (reduce +)))