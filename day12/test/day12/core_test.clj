(ns day12.core-test
  (:require [clojure.test :refer :all]
            [day12.core :refer :all]))


(defn close-to
  [x y epsilon]
  (<= (Math/abs (- x y)) epsilon))

(deftest test-distance
  (is (close-to 1.4142
                (distance [0 0] [1 1])
                0.001)))

(deftest test-parse-input
  (is (= 8 (count sample-input)))
  (is (= 5 (count (first sample-input))))
  (is (= "i" (nth (nth sample-input 7) 4))))

(deftest test-neighbours
  (is (= [[0 1] [1 0] [1 1]] (neighbours sample-input [0 0])))
  (is (= [[6 3] [6 4] [7 3]] (neighbours sample-input [7 4])))
  )