(ns day16.path
  (:require [clojure.data.priority-map :refer [priority-map]]))

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