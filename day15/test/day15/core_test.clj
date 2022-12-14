(ns day15.core-test
  (:require [clojure.test :refer :all]
            [day15.core :refer :all]))

(defn is= [a b]
  (is (= a b)))

(deftest test-distance
  (is= 9 (distance '(8 7) '(2 10))))

(def test-line [[8 7] [2 10]])

(deftest test-task1
  (is= 26 (task1 example-input 10))
  (is= 5838453 (task1 real-input 2000000))
  )

(deftest test-task2
  (is= 56000011 (task2 example-input 0 20))
  ;; (is= 12413999391794 (task2 real-input 0 4000000))
  )