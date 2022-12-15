(ns day12.core
  (:require [clojure.string :as str]
            [clojure.data.priority-map :refer [priority-map]]))

(defn distance [start goal]
  (Math/sqrt (+ (Math/pow (- (start 0) (goal 0)) 2)
                (Math/pow (- (start 1) (goal 1)) 2))))

(defn nth2 [map x y]
  (-> map
      (nth x)
      (nth y)
      (nth 0)))

(defn height [map x y]
  (let [val (int (nth2 map x y))]
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
                   (or (= dx 0)
                       (= dy 0))
                   (>= 1 (- (height map x y) (height map (start 0) (start 1)))))]
    [x y]))

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
(def real-input (parse-input (slurp "input")))

(defn successors [map]
  (fn [node]
    (let [ns (neighbours map node)]
      (zipmap ns (repeat (count ns) 1)))))

;; Inspired by https://gist.github.com/ummels/86c09182dee25b142280

(defn map-vals [m f]
  (into {} (for [[k v] m]
             [k (f v)])))

(defn remove-keys
  "Removes entries from map m with keys where (pred key) is true"
  [m pred]
  (select-keys m
               (filter (complement pred)
                       (keys m))))

(defn dijkstra
  "Computes single-source shortest path distances in a directed graph.
   Given a node n, (f n) should return a map with the successors of n as keys
   and their (non-negative) distance from n as vals.
   Returns a map from nodes to their distance from start."
  [start successors]
  (loop [q (priority-map start 0)
         result {}]
    (if-let [[node d] (peek q)]
      (let [dist (-> (successors node)
                     (remove-keys result)
                     (map-vals (partial + d)))]
        (recur (merge-with min (pop q) dist)
               (assoc result node d)))
      result)))

(defn search [map start end]
  (let [paths (dijkstra start (successors map))]
    (paths end)))