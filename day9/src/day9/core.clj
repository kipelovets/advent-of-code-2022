(ns day9.core
  (:require [clojure.string :as str]))

(defn abs [n] (max n (- n)))

(defn touching? [p1 p2]
  (or (= p1 p2)
      (and (>= 1 (abs (- (nth p1 0) (nth p2 0))))
           (>= 1 (abs (- (nth p1 1) (nth p2 1)))))))

(defn norm [x]
  (cond
    (= 0 x) 0
    (< x 0) -1
    :else 1))

(defn move-closer [head tail]
  (if (touching? head tail)
    tail
    (let [dx (norm (- (head 0) (tail 0)))
          dy (norm (- (head 1) (tail 1)))]
      [(+ (tail 0) dx) (+ (tail 1) dy)])))

(def dcoords {"L" [0 -1]
              "R" [0 1]
              "U" [-1 0]
              "D" [1 0]})

(defn coord-add [coord dcoord]
  [(+ (coord 0) (dcoord 0))
   (+ (coord 1) (dcoord 1))])

(defn move-dir [coord dir]
  (coord-add coord (dcoords dir)))

(defn move-knots [knots dir]
  (let [new-head (move-dir (first knots) dir)]
    (loop [tails (rest knots)
           new-knots [new-head]]
      (if (empty? tails)
        new-knots
        (recur (rest tails)
               (conj new-knots
                     (move-closer (last new-knots)
                                  (first tails))))))))

(defn follow-direction
  ([head tail dir steps]
   (follow-direction [head tail] dir steps))
  ([knots dir steps]
   (loop [tail-visited [(last knots)]
          steps-left steps
          cur-knots knots]
     (if (= 0 steps-left)
       [tail-visited cur-knots]
       (let [new-knots (move-knots cur-knots dir)
             new-tail (last new-knots)]
         (recur (conj tail-visited new-tail)
                (dec steps-left)
                new-knots))))))

(defn follow-motions [knots motions]
  (if (empty? motions)
    [(last knots)]
    (let [[dir steps] (first motions)
          [tail-visited new-knots] (follow-direction knots dir steps)]
      (concat tail-visited
              (follow-motions new-knots (rest motions))))))

(defn task1 [motions]
  (count (set (follow-motions [[0 0] [0 0]] motions))))

(defn task2 [motions]
  (count (set (follow-motions (repeat 10 [0 0]) motions))))