(ns day11.core
  (:require [clojure.string :as str]))

(defn parse-int [x] (Integer/parseInt x))

(defn parse-op [[op-str val-str]]
  (let [op (if (= op-str "*")
             *
             +)]
    (if (= val-str "old")
      #(op % %)
      #(op % (parse-int val-str)))))

(defn extract-int [s]
  (->> s
       (re-seq #"\d+")
       first
       parse-int))

(defn parse-monkey [lines]
  {:id (->> (nth lines 0)
            extract-int)
   :items (->> (nth lines 1)
               (re-seq #"\d+")
               (map parse-int))
   :op (->> (nth lines 2)
            (re-seq #"  Operation: new = old ([*+]) (.+)")
            first
            rest
            parse-op)
   :divisor (->> (nth lines 3)
                 extract-int)
   :if-true (->> (nth lines 4) extract-int)
   :if-false (->> (nth lines 5) extract-int)})

(defn parse-input [data]
  (let [monkeys (->> data
                     str/split-lines
                     (filter #(seq %))
                     (partition 6)
                     (map parse-monkey))]
    [(map #(dissoc % :items) monkeys)
     (map :items monkeys)]))

(defn throw-item [monkey item]
  (let [op (monkey :op)
        new-item (int (/ (op item) 3))
        test-result (= 0 (mod new-item (monkey :divisor)))
        target (monkey (if test-result :if-true :if-false))]
    [new-item target]))

(defn turn [monkey all-items]
  (let [id (monkey :id)
        items (nth all-items id)]
    (if (empty? items)
      all-items
      (let [item (first items)
            [new-item target] (throw-item monkey item)]
        (recur monkey (-> (vec all-items)
                          (assoc id (rest items))
                          (assoc target (conj (vec (nth all-items target)) new-item))))))))

(defn round [monkeys all-items]
  (loop [rest-monkeys monkeys
         cur-all-items all-items
         stats (repeat (count monkeys) 0)]
    (if (empty? rest-monkeys)
      [cur-all-items stats]
      (let [monkey (first rest-monkeys)
            id (monkey :id)]
        (recur (rest rest-monkeys)
               (turn monkey
                     cur-all-items)
               (update (vec stats)
                       id
                       #(+ % (count (nth cur-all-items id)))))))))

(defn keep-away
  ([monkeys all-items rounds]
   (keep-away monkeys all-items rounds (repeat (count monkeys) 0)))

  ([monkeys all-items rounds stats]
   (if (= 0 rounds)
     [all-items stats]
     (let [[new-all-items new-stats] (round monkeys all-items)]
       (recur monkeys
              new-all-items
              (dec rounds)
              (map + new-stats stats))))))

(defn monkey-business [monkeys all-items]
  (let [[_ stats] (keep-away monkeys all-items 20)]
    (->> stats
         (sort >)
         (take 2)
         (reduce *))))

(def example-input (parse-input (slurp "sample-input")))
(def example-monkeys (first example-input))
(def example-items (last example-input))

