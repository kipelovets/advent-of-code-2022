(ns day3.core-test
  (:require [clojure.test :refer :all]
            [day3.core :refer :all]
            [clojure.string :as str]))

(def sample-input "vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw")
(def sample-lines (str/split-lines sample-input))

(def real-lines (str/split-lines (slurp "input")))

(deftest sample-data
  (testing "parse-input"
    (is (= #{\p} (illegal-items (first sample-lines))))
    (is (= #{\L} (illegal-items (get sample-lines 1))))
    (is (= #{\P} (illegal-items (get sample-lines 2))))
    (is (= #{\v} (illegal-items (get sample-lines 3))))
    (is (= #{\t} (illegal-items (get sample-lines 4))))
    (is (= #{\s} (illegal-items (get sample-lines 5)))))

  (testing "priority"
    (is (= 16 (priority \p)))
    (is (= 38 (priority \L)))
    (is (= 42 (priority \P)))
    (is (= 1 (priority \a)))
    (is (= 26 (priority \z)))
    (is (= 27 (priority \A)))
    (is (= 52 (priority \Z))))

  (testing "task1"
    (is (= 157 (task1 sample-lines)))))

(deftest real-data
  (testing "task1"
    (is (= 7553 (task1 real-lines)))))

(deftest test-task2
  (testing "sample-data"
    (is (= 70 (task2 sample-lines))))

  (testing "real-data"
    (is (= 2758 (task2 real-lines)))))