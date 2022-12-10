(ns day10.core
  (:require [clojure.string :as str]))

(defn parse-input [data]
  (->> data
       str/split-lines
       (map (fn [row]
              (let [[dir steps] (str/split row #" ")]
                (if (= nil steps)
                  [dir]
                  [dir (Integer/parseInt steps)]))))))

(def interesting-cycles #{20 60 100 140 180 220})

(defn interesting-cycle [cycle cmd]
  (cond
    (contains? interesting-cycles cycle) cycle
    (and (= "addx" cmd)
         (contains? interesting-cycles (inc cycle))) (inc cycle)
    :else 0))

(defn task1 [program]
  (loop [cycle 1
         register 1
         rest-program program
         signal-strength-sum 0]
    (if (empty? rest-program)
      signal-strength-sum
      (let [[cmd arg] (first rest-program)
            cmd-cycles (if (= "noop" cmd) 1 2)
            signal-strength (* (interesting-cycle cycle cmd) register)]
        (recur (+ cycle cmd-cycles)
               (+ register (if (= "addx" cmd) arg 0))
               (rest rest-program)
               (+ signal-strength-sum signal-strength))))))

(defn abs [n] (max n (- n)))

(defn pixel-in-sprite? [cycle register]
  (>= 1 (abs (- cycle register))))

(defn pixel-from-cycle [cycle]
  (rem (dec cycle) 40))

(defn task2 [program]
  (loop [cycle 1
         register 1
         rest-program program]
    (let [[cmd arg] (first rest-program)
          cmd-cycles (if (= "noop" cmd) 1 2)
          pixel (pixel-from-cycle cycle)
          pixel-in-sprite (pixel-in-sprite? pixel register)]
      (print (if pixel-in-sprite "#" "."))
      (when (= 0 (rem cycle 40))
        (println))
      (when (= 2 cmd-cycles)
        (print (if (pixel-in-sprite? (pixel-from-cycle (inc cycle)) register)
                 "#" "."))
        (when (= 0 (rem (inc cycle) 40))
          (println)))

      (when (seq rest-program)
        (recur (+ cycle cmd-cycles)
               (+ register (if (= "addx" cmd) arg 0))
               (rest rest-program))))))

(def example-program (parse-input (slurp "sample-input")))
(task2 example-program)

(println)
(println)
(println)

(def real-program (parse-input (slurp "input")))
(task2 real-program)

