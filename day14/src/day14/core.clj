(ns day14.core
  (:require [clojure.string :as str]))

(defn parse-coord [coord]
  (->> (str/split coord #",")
       (map #(Integer/parseInt %))
       vec))

(defn parse-line [line]
  (->> (str/split line #" -> ")
       (map parse-coord)
       vec))

(defn parse-input [fname]
  (->> fname
       slurp
       str/split-lines
       (map parse-line)
       vec))


(def example-input (parse-input "example-input"))
(def real-input (parse-input "input"))

(defn axis-bounds [axis]
  [(apply min axis) (apply max axis)])

(defn input-bounds [input]
  (let [coords (conj (reduce concat input) [500 0])
        [minx maxx] (axis-bounds (map first coords))
        [miny maxy] (axis-bounds (map last coords))]
    [[minx miny] [maxx maxy]]))

(defn within-bounds? [[x y] input]
  (let [[[minx miny] [maxx maxy]] (input-bounds input)]
    (and (>= x minx)
         (<= x maxx)
         (>= y miny)
         (<= y maxy))))

(defn path-contains [point line-from line-to]
  (or (and (= (first point) (first line-from) (first line-to))
           (>= (last point) (min (last line-from) (last line-to)))
           (<= (last point) (max (last line-from) (last line-to))))
      (and (= (last point) (last line-from) (last line-to))
           (>= (first point) (min (first line-from) (first line-to)))
           (<= (first point) (max (first line-from) (first line-to))))))

(defn f-or [a b]
  (or a b))

(defn structure-contains [point structure]
  (->> structure
       count
       dec
       range
       (map #(vector (nth structure %) (nth structure (inc %))))
       (map #(path-contains point (first %) (last %)))
       (reduce f-or)))

(defn free? [point input sand]
  (if (contains? (set sand) point)
    false
    (not (->> input
              (map #(structure-contains point %))
              (take-while false?)
              count
              (> (count input))))))

(defn drop-sand [[x y] input sand]
  (let [candidates [[x (inc y)]
                    [(dec x) (inc y)]
                    [(inc x) (inc y)]]
        free-cells (->> candidates
                        (filter #(free? % input sand)))
        next (->> free-cells
                  first)]
    (cond (empty? free-cells) [x y]
          (within-bounds? next input) (recur next input sand)
          :else nil)))

(defn keep-dropping-sand
  ([input]
   (keep-dropping-sand input #{}))
  ([input sand]
   (let [next-rest (drop-sand [500 0] input sand)]
     (if (nil? next-rest)
       sand
       (recur input (conj sand next-rest))))))

(defn print-cave [input sand]
  (let [[[minx miny] [maxx maxy]] (input-bounds input)]
    (first (doall (for [y (range miny (inc maxy))
                        x (range minx (inc maxx))]
                    (do (print (cond (= [500 0] [x y]) "+"
                                     (free? [x y] input sand) "."
                                     (contains? sand [x y]) "o"
                                     :else "#"))
                        (when (= x maxx)
                          (println))))))))

(defn drop-sand-units [input sand units]
  (if (= 0 units)
    sand
    (recur input
           (conj sand (drop-sand [500 0] input sand))
           (dec units))))

