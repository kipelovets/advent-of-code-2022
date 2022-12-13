(ns day12.core
  (:require [clojure.string :as str]))

(defn distance [start goal]
  (Math/sqrt (+ (Math/pow (- (start 0) (goal 0)) 2)
                (Math/pow (- (start 1) (goal 1)) 2))))

(defn nth2 [map x y]
  (let [val (-> map
                (nth x)
                (nth y)
                (nth 0)
                int)]
    (cond
      (= val (int \S)) (int \a)
      (= val (int \E)) (int \z)
      :else val)))

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
                       (not= y (start 1)))
                   (>= 1 (- (nth2 map x y) (nth2 map (start 0) (start 1)))))]
    [x y]))

(defn search [map start goal visited]
  (let [next-nodes (neighbours map start)
        new-path-len (inc (count visited))]
    (apply min ##Inf (for [node next-nodes
                           :when (not (.contains visited node))]
                       (if (= node goal)
                         new-path-len
                         (search map node goal (conj visited node)))))))


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

(search sample-input [0 0] [5 2] [])