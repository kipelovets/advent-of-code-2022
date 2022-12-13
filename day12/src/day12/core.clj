(ns day12.core
  (:require [clojure.string :as str]))

(defn distance [start goal]
  (Math/sqrt (+ (Math/pow (- (start 0) (goal 0)) 2)
                (Math/pow (- (start 1) (goal 1)) 2))))

(defn neighbours [map start]
  (for [dx (range -1 2)
        dy (range -1 2)
        :let [x (+ dx (start 0))
              y (+ dy (start 1))]
        :when (and (>= x 0)
                   (>= y 0)
                   (< x (count map))
                   (< y (count (first map)))
                   (or (not= x (start 0))
                       (not= y (start 1))))]
    [x y]))

(defn search [map start goal]
  (loop [path [start]]
    (let [next (neighbours map start)])))


(def sample-data "Sabqponm
abcryxxl
accszExk
acctuvwj
abdefghi")

(defn parse-input [data]
  (->> data
       str/split-lines
       (map #(str/split % #""))
       (apply map list)))

(def sample-input (parse-input sample-data))
