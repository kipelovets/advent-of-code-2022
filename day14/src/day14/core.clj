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

(defn drop-sand [[x y] is-free is-abyss]
  (let [candidates [[x (inc y)]
                    [(dec x) (inc y)]
                    [(inc x) (inc y)]]
        free-cells (->> candidates
                        (filter is-free))
        next (->> free-cells
                  first)]
    (cond (empty? free-cells) [x y]
          (not (is-abyss [x y])) (recur next is-free is-abyss)
          :else nil)))

(defn build-rocks [input]
  (let [[[minx miny] [maxx maxy]] (input-bounds input)]
    (set (for [y (range miny (inc maxy))
               x (range minx (inc maxx))
               :when (not (free? [x y] input #{}))]
           [x y]))))

(def sand-hole [500 0])

(defn keep-dropping-sand [is-free-from-rocks is-abyss]
  (loop [sand #{}]
    (prn (count sand))
    (let [is-free #(and (not (sand %))
                        (is-free-from-rocks %))
          next-rest (drop-sand sand-hole is-free is-abyss)]
      (if (or (nil? next-rest)
              (= sand-hole next-rest))
        sand
        (recur (conj sand next-rest))))))

(defn task1 [input]
  (count (keep-dropping-sand #(free? % input #{})
                             #(not (within-bounds? % input)))))

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

(defn drop-sand-units
  ([input sand units]
   (drop-sand-units #(not (within-bounds? % input))
                    (build-rocks input)
                    sand
                    units))
  ([is-abyss rocks sand units]
   (let [is-free #(not (or (sand %) (rocks %)))]
     (if (= 0 units)
       sand
       (recur is-abyss
              rocks
              (conj sand (drop-sand sand-hole is-free is-abyss))
              (dec units))))))

(defn task2 [input]
  (let [[_ [_ maxy]] (input-bounds input)
        is-free #(and (free? % input #{})
                      (< (last %) (+ maxy 2)))
        is-abyss (constantly false)]
    (-> (keep-dropping-sand is-free is-abyss)
        count
        inc)))