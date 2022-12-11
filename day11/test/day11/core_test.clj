(ns day11.core-test
  (:require [clojure.test :refer :all]
            [day11.core :refer :all]))

(def example-input (parse-input (slurp "sample-input")))
(def example-monkeys (first example-input))
(def example-items (last example-input))

(deftest test-parse-input
  (let [monkey (first example-monkeys)
        op (monkey :op)]
    (testing "length"
      (is (= 4 (count example-monkeys))))
    (testing "monkeys"
      (is (= 0 (monkey :id)))
      (is (= 23 (monkey :divisor)))
      (is (= 2 (monkey :if-true)))
      (is (= 3 (monkey :if-false)))
      (is (= 57 (op 3))))
    (testing "third monkey op"
      (is (= 9 (let [op (:op (nth example-monkeys 2))]
                 (op 3)))))
    (testing "items"
      (is (= [79 98] (first example-items))))))

(deftest test-parse-op
  (testing "returns function"
    (is (fn? (parse-op ["*" "10"]))))

  (testing "result is working"
    (let [op (parse-op ["*" "10"])]
      (is (= 30 (op 3))))))

(deftest test-throw-item
  (testing "first example round"
    (testing "monkey 0"
      (are [item target]
           (= target (last (throw-item (first example-monkeys) item initial-worry-relief)))
        79 3
        98 3))

    (testing "monkey 1"
      (are [item target]
           (= target (last (throw-item (nth example-monkeys 1) item initial-worry-relief)))
        54 0
        65 0
        75 0
        74 0)))
  (testing "monkey 2"
    (are [item target]
         (= target (last (throw-item (nth example-monkeys 2) item initial-worry-relief)))
      79 1
      60 3
      97 3))

  (testing "monkey 3"
    (are [item target]
         (= target (last (throw-item (nth example-monkeys 3) item initial-worry-relief)))
      74 1
      500 1
      620 1
      1200 1
      3136 1)))

(deftest test-turn
  (testing "monkey 0"
    (is (= [[] [54 65 75 74] [79 60 97] [74 500 620]]
           (turn (first example-monkeys) example-items initial-worry-relief)))))

(deftest test-round
  (testing "round 1"
    (let [[result stats] (round example-monkeys example-items initial-worry-relief)]
      (is (= [[20 23 27 26]
              [2080 25 167 207 401 1046]
              []
              []] result))
      (is (= [2 4 3 5] stats)))))

(deftest test-20-rounds
  (testing "example input"
    (is (= [[[10 12 14 26 34]
             [245 93 53 199 115]
             []
             []]
            [101 95 7 105]] (keep-away example-monkeys example-items 20 initial-worry-relief)))))

(def real-input (parse-input (slurp "input")))
(def real-monkeys (first real-input))
(def real-items (last real-input))

(deftest test-monkey-business
  (testing "example input"
    (is (= 10605 (monkey-business example-monkeys example-items initial-worry-relief 20)))
    (is (= 57838 (monkey-business real-monkeys real-items initial-worry-relief 20)))))

(def example-relief (make-relief example-monkeys))
(def real-relief (make-relief real-monkeys))

(deftest test-task2
  (testing "example input"
    (testing "round"
      (is (= [2 4 3 6] (last (round example-monkeys example-items example-relief)))))
    (testing "keep-away"
      (is (= [99 97 8 103] (last (keep-away example-monkeys example-items 20 example-relief))))
      (is (= [5204 4792 199 5192] (last (keep-away example-monkeys example-items 1000 example-relief))))
      (is (= [52166 47830 1938 52013] (last (keep-away example-monkeys example-items 10000 example-relief)))))
    (testing "monkey-business"
      (is (= 2713310158 (monkey-business example-monkeys example-items example-relief 10000)))))
  
  (testing "real input"
    (is (= 15050382231 (monkey-business real-monkeys real-items real-relief 10000)))))

