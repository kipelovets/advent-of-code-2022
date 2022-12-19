(ns day13.core-test
  (:require [clojure.test :refer :all]
            [day13.core :refer :all]))

(defn example-compare [ind]
  (apply compare-packets (nth example-input ind)))

(deftest test-parse-input
  (testing "example-data"
    (is (= ['([1 1 3 1 1] [1 1 5 1 1])
            '([[1] [2 3 4]] [[1] 4])
            '([9] [[8 7 6]])
            '([[4 4] 4 4] [[4 4] 4 4 4])
            '([7 7 7 7] [7 7 7])
            '([] [3])
            '([[[]]] [[]])
            '([1 [2 [3 [4 [5 6 7]]]] 8 9] [1 [2 [3 [4 [5 6 0]]]] 8 9])]
           example-input))))

(deftest test-compare-packets
  (testing "example-data"
    (is (true? (example-compare 0)))
    (is (true? (example-compare 1)))
    (is (false? (example-compare 2)))
    (is (true? (example-compare 3)))
    (is (false? (example-compare 4)))
    (is (true? (example-compare 5)))
    (is (false? (example-compare 6)))
    (is (false? (example-compare 7)))))

(def real-input (parse-input "input"))

(deftest test-task1
  (testing "example-data"
    (is (= 13 (task1 example-input))))

  (testing "real-data"
    (is (= 6272 (task1 real-input)))))

(deftest test-task2
  (testing "example-data"
    (is (= 140 (task2 example-input))))
  (testing "real-data"
    (is (= 22288 (task2 real-input)))))