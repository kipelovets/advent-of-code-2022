(ns day16.core
  (:require [clojure.string :as str]
            [day16.path :refer [dijkstra]]))

(defn parse-line [line]
  (let [valves (re-seq #"[A-Z]{2}" line)
        pressure (re-seq #"\d+" line)]
    {:id (first valves)
     :pressure (Integer/parseInt (first pressure))
     :links (rest valves)}))

(defn parse-input [fname]
  (let [lines (->> (slurp fname)
                   str/split-lines
                   (map parse-line))]
    (zipmap (map #(:id %) lines)
            lines)))

(def example-input (parse-input "example-input"))

(defn total-pressure [input open-valves]
  (->> open-valves
       (map #(* (:pressure (input (last %))) (- 30 (first %))))
       (reduce +)))

(defn successors [input]
  (fn [node]
    (let [links (:links (input node))]
      (zipmap links (repeat (count links) 1)))))

(defn with-paths [input]
  (let [paths (zipmap (keys input)
                      (for [node (keys input)]
                        (dijkstra node (successors input))))]
    (into {} (for [[id npaths] paths] [id (merge (input id) {:paths npaths})]))))

(defn non-zero-valves [input]
  (->> input
       (filter #(< 0 (:pressure (last %))))
       (map first)))

(defn next-nodes [input open-valves]
  (let [open-valves-names (set (map last open-valves))]
    (filter #(not (contains? open-valves-names %))
            (non-zero-valves input))))

(defn task1
  ([input]
   (task1 (with-paths input) "AA" 1 []))
  ([input node minute open-valves]
   (let [non-zero-valves-left (next-nodes input open-valves)]
     (if (empty? non-zero-valves-left)
       (let [res (total-pressure input open-valves)]
         (prn open-valves res)
         res)
       (apply max (for [next non-zero-valves-left]
                    (if (= node next)
                      (task1 input
                             node
                             (inc minute)
                             (conj open-valves [(inc minute) node]))
                      (task1 input
                             next
                             (inc (+ minute (-> input (get node) :paths (get next))))
                             (conj open-valves [(inc minute) next])))))))))

(task1 example-input)
