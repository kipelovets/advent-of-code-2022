(ns day15.core
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn or* [a b] (or a b))

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

(def example-input (parse-input "example-input"))
(def real-input (parse-input "input"))

(defn coverage [[sensor beacon] y]
  (let [srange (distance sensor beacon)
        sx (first sensor)
        dd (- srange (distance sensor [sx y]))]
    (if (>= 0 dd)
      nil
      [(- sx dd) (inc (+ sx dd))])))

(defn in-ranges [ranges x]
  (reduce or* (map #(and (>= x (first %))
                         (<= x (last %))) ranges)))

(defn covered-on-y [input y]
  (let [coverages (->> input
                       (map #(coverage % y))
                       (filter some?))
        minx (apply min (map first coverages))
        maxx (apply max (map last coverages))]
    (for [x (range minx (inc maxx))
          :when (in-ranges coverages x)]
      x)))

(defn task1 [input y]
  (let [coverages (covered-on-y input y)
        cov-count (count coverages)
        beacons-on-y (->> input
                          (map last)
                          (filter #(= y (last %)))
                          set
                          count)]
    (dec (- cov-count beacons-on-y))))

(defn range-intersect? [a b]
  (or (and (<= (first a) (first b))
           (>= (last a) (first b)))
      (and (<= (first a) (last b))
           (>= (last a) (last b)))))

(defn range-merge [a b]
  [(min (first a) (first b))
   (max (last a) (last b))])

(defn free-on-y [input y]
  (let [coverages (->> input
                       (map #(coverage % y))
                       (filter some?)
                       sort)]
    (loop [cov (first coverages)
           covs (rest coverages)]
      (if (empty? covs)
        nil
        (if (not (range-intersect? cov (first covs)))
          (inc (last cov))
          (recur (range-merge cov (first covs)) (rest covs)))))))


(defn find-free [input from to]
  (first (for [y (range from (inc to))
               :let [x (free-on-y input y)]
               :when (do
                       (prn y)
                       (some? x))]
           [(dec x) y])))

(defn task2 [input from to]
  (let [[x y] (find-free input from to)]
    (+ (* 4000000 x) y)))