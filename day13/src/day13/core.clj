(ns day13.core
  (:require [clojure.string :as str]))

(defn parse-packet
  ([packet]
   (parse-packet (rest packet) [[]] [0]))

  ([left result cur-path]
   (let [next (first left)]
     (cond
       (empty? left) result
       (= \[ next) (recur (rest left)
                          (update-in result cur-path #(conj (vec %) []))
                          (conj cur-path (count (get-in result cur-path))))
       (= \] next) (recur (rest left)
                          result
                          (pop cur-path))
       :else (recur (rest left)
                    (update-in result cur-path #(conj (vec %) next))
                    cur-path)))))

(defn parse-line [line]
  (->> line
       (re-seq #"[\[\]]|\d+")
       (map #(if (re-seq #"\d+" %)
               (Integer/parseInt %)
               (first %)))
       parse-packet
       first))

(defn parse-input [fname]
  (->> (slurp fname)
       (str/split-lines)
       (filter not-empty)
       (map parse-line)
       (partition 2)))

(defn ensure-vec [val]
  (if (int? val) [val] val))

(defn compare-packets [left-packet right-packet]
  (if (and (empty? left-packet) (empty? right-packet))
    nil
    (let [left (first left-packet)
          right (first right-packet)]
      (cond

        (nil? left) true
        (nil? right) false
        (and (int? left) (int? right)) (cond
                                         (< left right) true
                                         (> left right) false
                                         :else (recur (rest left-packet) (rest right-packet)))
        :else (let [list-comp (compare-packets (ensure-vec left) (ensure-vec right))]
                (if (some? list-comp)
                  list-comp
                  (recur (rest left-packet) (rest right-packet))))))))

(defn task1 [input]
  (->> input
       (map #(apply compare-packets %))
       (map-indexed #(vector %1 %2))
       (filter #(last %))
       (map first)
       (map inc)
       (reduce +)))

(defn xxx [results]
  (let [ind1 (count (nth results 0))
        ind2 (count (nth results 2))]
    (* (inc ind1)
       (+ ind1 ind2 2))))

(defn task2 [input]
  (->> input
       (reduce concat)
       (concat [[[2]] [[6]]])
       (sort compare-packets)
       (partition-by #(or (= % [[2]]) (= % [[6]])))
       xxx))

(def example-input (parse-input "example-input"))