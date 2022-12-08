(ns day8.core
  (:require [clojure.string :as str]))

(def sample-input "30373
25512
65332
33549
35390")

(defn ^:private parse-input [data]
  (->> data
       str/split-lines
       (map #(str/split % #""))
       (map (fn [row]
              (map #(Integer/parseInt %) row)))))

(def sample-forest (parse-input sample-input))
(def real-forest (parse-input (slurp "input")))

(defn ^:private visible-in-row? [row x]
  (let [tree (nth row x)
        hiding-trees #(>= % tree)
        before (take x row)
        after (drop (inc x) row)]
    (or (empty? (filter hiding-trees before))
        (empty? (filter hiding-trees after)))))

(defn visible? [forest y x]
  (cond
    (or (= x 0) (= y 0)) true
    (= y (count forest)) true
    (= x (count (first forest))) true
    :else (or (visible-in-row? (nth forest y) x)
              (visible-in-row? (map #(nth % x) forest) y))))

(defn task1 [forest]
  (reduce + (for [y (range (count forest))
                  x (range (count (first forest)))]
              (if (visible? forest y x) 1 0))))