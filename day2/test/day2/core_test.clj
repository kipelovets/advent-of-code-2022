(ns day2.core-test
  (:require [clojure.test :refer :all]
            [day2.core :refer :all]))

(def test-input "A Y
B X
C Z")

(deftest testing-data
  (testing "is-draw"
    (is (= true (is-draw "C Z"))))

  (testing "move-score"
    (is (= 8 (move-score "A Y")))
    (is (= 1 (move-score "B X")))
    (is (= 6 (move-score "C Z"))))

  (testing "full-score"
    (is (= 15 (score test-input)))))

(deftest real-data
  (testing "task1"
    (is (= 11150 (score (slurp "input"))))))