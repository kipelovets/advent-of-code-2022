(ns day12.core
  (:require [clojure.string :as str]))

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

(defn search
  ([map start goal]
   (dec (search map start goal [start])))
  ([map start goal visited]
   (let [next-nodes (neighbours map start)
         new-path-len (inc (count visited))]
     (apply min ##Inf (for [node next-nodes
                            :when (not (.contains visited node))]
                        (if (= node goal)
                          new-path-len
                          (search map node goal (conj visited node))))))))


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

(defn find-start-and-goal [map]
  (let [result (for [x (range (count map))
                     y (range (count (first map)))
                     :let [symbol (nth2 map x y)]
                     :when (or (= \S symbol)
                               (= \E symbol))]
                 {symbol [x y]})
        result (apply merge result)]
    [(result \S) (result \E)]))