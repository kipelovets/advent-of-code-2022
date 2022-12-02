(ns day2.core-test
  (:require [clojure.test :refer :all]
            [day2.core :refer :all]
            [clojure.string :as str]))

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
    (is (= 15 (score test-input))))

  (testing "replace-intent-with-move"
    (is (= "A X" (replace-intent-with-move "A Y")))
    (is (= "B X" (replace-intent-with-move "B X")))
    (is (= "C X" (replace-intent-with-move "C Z"))))

  (testing "task2"
    (is (= 12 (score (str/join "\n" (map replace-intent-with-move (str/split-lines test-input))))))))

(deftest real-data
  (testing "task1"
    (is (= 11150 (score (slurp "input")))))
  
  (testing "task2"
    (is (= 8295 (score (str/join "\n" (map replace-intent-with-move (str/split-lines (slurp "input")))))))))