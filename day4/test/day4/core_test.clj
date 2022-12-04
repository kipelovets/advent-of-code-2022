(ns day4.core-test
  (:require [clojure.test :refer :all]
            [day4.core :refer :all]
            [clojure.string :as str]))

(def sample-input "2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8")

(def parsed (parse-input sample-input))

(deftest test-sample-input
  (testing "parse-input"
    (is (= 6 (count parsed)))
    (is (= [[2 3 4] [6 7 8]] (first parsed))))

  (testing "pair contains overlap"
    (let [overlaps (map pair-has-overlap parsed)]
      (is (= [false false false true true false] overlaps))))

  (testing "task1"
    (is (= 2 (task1 sample-input)))))

(def real-input (slurp "input"))

(deftest test-real-input
  (testing "task1"
    (is (= 651 (task1 real-input)))))


