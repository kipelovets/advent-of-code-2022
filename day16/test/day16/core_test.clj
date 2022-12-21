(ns day16.core-test
  (:require [clojure.test :refer :all]
            [day16.core :refer :all]))

(deftest test-total-pressure
  (is (= 364 (total-pressure example-input [[2 "BB"]])))
  (is (= 1651 (total-pressure example-input [[2 "DD"] [5 "BB"] [9 "JJ"] [17 "HH"] [21 "EE"] [24 "CC"]]))))

(deftest test-non-zero-valves
  (is (= ["JJ" "HH" "DD" "CC" "BB" "EE"] (non-zero-valves example-input)))
  (is (= 15 (count (non-zero-valves real-input)))))

(deftest test-next-nodes
  (is (= ["CC" "BB" "EE"] (next-nodes example-input [[1 "JJ"] [2 "HH"] [3 "DD"]]))))

(deftest test-task1
  (is (= 1651 (task1 example-input)))
  (is (= 2330 (task1 real-input))))