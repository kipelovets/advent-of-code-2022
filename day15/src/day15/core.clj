(ns day15.core
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn and* [a b]
  (and a b))

(def line-re
  #"Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)")

(defn parse-int [val]
  (Integer/parseInt val))

(defn parse-line [line]
  (->> (re-seq line-re line)
       first
       rest
       (map parse-int)
       (partition 2)))

(defn parse-input [fname]
  (->> (slurp fname)
       str/split-lines
       (map parse-line)))

(defn distance [a b]
  (+ (Math/abs (- (first a) (first b)))
     (Math/abs (- (last a) (last b)))))

(defn x-bounds [input]
  (let [sensor-bounds (map (fn [[sensor beacon]]
                             (let [dist (distance sensor beacon)]
                               [(- (first sensor) dist)
                                (+ (first sensor) dist)])) input)]
    [(->> sensor-bounds
          (map first)
          (apply min))
     (->> sensor-bounds
          (map last)
          (apply max))]))

(def example-input (parse-input "example-input"))
(def real-input (parse-input "input"))

(defn coverage [[sensor beacon] y]
  (let [srange (distance sensor beacon)
        sx (first sensor)
        dd (- srange (distance sensor [sx y]))]
    (if (>= 0 dd)
      []
      (range (- sx dd) (inc (+ sx dd))))))

(defn task1 [input y]
  (let [coverages (->> input
                       (map #(coverage % y))
                       (map set)
                       (reduce set/union)
                       count)
        beacons-on-y (->> input
                          (map last)
                          (filter #(= y (last %)))
                          set
                          count)]
    (- coverages beacons-on-y)))
