(ns day2.core
  (:require [clojure.string :as str]))

(def opponent-winning-combinations #{"A Z" "B X" "C Y"})

(def shape-score {"X" 1 "Y" 2 "Z" 3})

(defn replace-intent-with-move [move]
  (let [[opponent-move my-move] (str/split move #" ")
        losing-move (first (filter #(re-matches (re-pattern opponent-move) %) 
                                   opponent-winning-combinations))
        ; TODO
        ]))

(defn is-draw [move]
  (let [[opponent-move my-move] (str/split move #" ")
        opponent-char (int (get opponent-move 0))
        my-char (int (get my-move 0))
        opponent-value (- opponent-char (int \A))
        my-value (- my-char (int \X))]
    (= opponent-value
       my-value)))

(defn move-score [move]
  (let [[_ my-move] (str/split move #" ")]
    (+ (shape-score my-move)
       (cond
         (is-draw move) 3
         (contains? opponent-winning-combinations move) 0
         :else 6))))

(defn score [moves]
  (reduce + (map move-score (str/split-lines moves))))