(ns day16.core-test
  (:require [clojure.test :refer :all]
            [day16.core :refer :all]))

(deftest test-total-pressure
  (is (= 364 (total-pressure example-input [[2 "BB"]])))
  (is (= 1651 (total-pressure example-input [[2 "DD"] [4 "BB"] [7 "JJ"] [11 "HH"] [19 "EE"] [23 "CC"]]))))

(deftest test-non-zero-valves
  (is (= ["JJ" "HH" "DD" "CC" "BB" "EE"] (non-zero-valves example-input))))

(deftest test-next-nodes
  (is (= ["CC" "BB" "EE"] (next-nodes example-input [[1 "JJ"] [2 "HH"] [3 "DD"]]))))

;; (deftest test-task1
;;   (is (= 0 (task1 example-input))))