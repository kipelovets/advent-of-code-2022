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
  (reduce + (map priority (reduce concat (map illegal-items rucksacks)))))

(defn task2 [rucksacks]
  (let [groups (partition 3 rucksacks)
        common-items (map (fn [group] (apply set/intersection (map set group))) groups)
        flat-items (reduce concat common-items)]
    (reduce + (map priority flat-items))))