(ns day11.core
  (:require [clojure.string :as str]))

(defn parse-int [x] (Integer/parseInt x))

(defn parse-op [[op-str val-str]]
  (let [op (if (= op-str "*")
             *
             +)
        op-on-old (= val-str "old")
        val (if op-on-old  0 (parse-int val-str))]
    (if op-on-old
      (fn [x] (op x x))
      (fn [x] (op x val)))))

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

(defn initial-worry-relief [n] (/ n 3))
(defn make-relief [monkeys]
  (let [modulo (reduce * (map :divisor monkeys))]
    (fn ^long [^long n] (mod n modulo))))


(defn throw-item [monkey item worry-relief]
  (let [op (monkey :op)
        new-item (long (worry-relief (op item)))
        test-result (= 0 (mod new-item (monkey :divisor)))
        target (monkey (if test-result :if-true :if-false))]
    [new-item target]))

(defn turn [monkey all-items worry-relief]
  (let [id (monkey :id)
        items (nth all-items id)]
    (if (empty? items)
      all-items
      (let [item (first items)
            [new-item target] (throw-item monkey item worry-relief)]
        (recur monkey
               (-> (vec all-items)
                   (assoc id (rest items))
                   (assoc target (conj (vec (nth all-items target)) new-item)))
               worry-relief)))))

(defn round [monkeys all-items worry-relief]
  (loop [rest-monkeys monkeys
         cur-all-items all-items
         stats (repeat (count monkeys) 0)]
    (if (empty? rest-monkeys)
      [cur-all-items stats]
      (let [monkey (first rest-monkeys)
            id (monkey :id)]
        (recur (rest rest-monkeys)
               (turn monkey
                     cur-all-items
                     worry-relief)
               (update (vec stats)
                       id
                       #(+ % (count (nth cur-all-items id)))))))))

(defn keep-away
  ([monkeys all-items rounds worry-relief]
   (keep-away monkeys all-items rounds worry-relief (repeat (count monkeys) 0)))

  ([monkeys all-items rounds worry-relief stats]
   (if (= 0 rounds)
     [all-items stats]
     (let [[new-all-items new-stats] (round monkeys all-items worry-relief)]
       (recur monkeys
              new-all-items
              (dec rounds)
              worry-relief
              (map + new-stats stats))))))

(defn monkey-business [monkeys all-items worry-relief rounds]
  (let [[_ stats] (keep-away monkeys all-items rounds worry-relief)]
    (->> stats
         (sort >)
         (take 2)
         (reduce *))))
