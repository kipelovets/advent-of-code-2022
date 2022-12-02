(ns day2.core
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def opponent-winning-combinations #{"A Z" "B X" "C Y"})

(def shape-score {"X" 1 "Y" 2 "Z" 3})

(defn replace-intent-with-move [move]
  (let [[opponent-move my-move] (str/split move #" ")
        losing-combination (first (filter #(re-matches (re-pattern (str opponent-move "..")) %)
                                          opponent-winning-combinations))
        losing-move (-> losing-combination
                        (str/split #" ")
                        (second))
        draw-move (str (char (+ (int \X) (- (int (get opponent-move 0)) (int \A)))))
        winning-move (first (set/difference #{"X" "Y" "Z"} #{losing-move draw-move}))]
    (str opponent-move " " (case my-move
                             "X" losing-move
                             "Y" draw-move
                             "Z" winning-move))))

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