(ns day5.core
  (:require [clojure.string :as str]))

(defn transpose [xs]
  (apply map list xs))

(defn parse-input [input]
  (let [[stacks-lines moves-lines] (->> input
                                        str/split-lines
                                        (partition-by #(= % ""))
                                        (filter #(not (= % [""]))))
        stacks (->> stacks-lines
                    drop-last
                    (map #(re-seq #"(?:(?:\[\w\])|(?:   )) ?" %))
                    (map (fn [line]
                           (map #(str/replace (str/trim %) #"\[|\]" "") line)))
                    transpose
                    (map (fn [line]
                           (filter #(not (= % "")) line))))
        moves (map #(->> %
                         (re-seq #"move (\d+) from (\d+) to (\d+)")
                         first
                         (drop 1)
                         (map (fn [x] (Integer/parseInt x)))) moves-lines)]
    [stacks moves]))

(defn apply-move [stacks from to count]
  (let [from-ind (dec from)
        to-ind (dec to)
        stacks-vec (vec stacks)
        items (take count (get stacks-vec from-ind))
        from-updated (vec (drop count (get stacks-vec from-ind)))
        to-updated (vec (concat items (get stacks-vec to-ind)))]
    (-> stacks-vec
        (assoc from-ind from-updated)
        (assoc to-ind to-updated))))

(defn apply-moves [stacks from to count]
  (if (= 0 count)
    stacks
    (recur (apply-move stacks from to 1) from to (dec count))))

(defn apply-all-moves [stacks moves]
  (if (empty? moves)
    stacks
    (let [[count from to] (first moves)
          rest-moves (rest moves)]
      (recur (apply-moves stacks from to count) rest-moves))))

(defn task1 [stacks moves]
  (let [result (apply-all-moves stacks moves)]
    (str/join "" (map first result))))

(defn task2 [stacks moves]
  (let [result (loop [s stacks
                      m moves]
                 (if (empty? m)
                   s
                   (let [[count from to] (first m)]
                     (recur (apply-move s from to count) (rest m)))))]
    (str/join "" (map first result))))