(ns day10.core-test
  (:require [clojure.test :refer :all]
            [day10.core :refer :all]))

(def small-input "noop
addx 3
addx -5")

(def small-program (parse-input small-input))

(deftest test-parse-input
  (testing "small input"
    (is (= [["noop"]
            ["addx" 3]
            ["addx" -5]]
           (parse-input small-input)))))

(deftest test-task1
  (testing "small input"
    (is (= 0 (task1 small-program))))

  (testing "example input"
    (is (= 13140 (task1 example-program))))

  (testing "real input"
    (is (= 14220 (task1 real-program)))))

(deftest test-pixel-in-sprite?
  (testing "test"
    (are [result cycle register]
         (= result (pixel-in-sprite? cycle register))
      true 1 1
      true 1 2
      false 1 3
      false 3 1)))