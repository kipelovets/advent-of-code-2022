(ns day5.core-test
  (:require [clojure.test :refer :all]
            [day5.core :refer :all]))

(def sample-input "    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2")

(def parsed-sample-input (parse-input sample-input))
(def sample-stacks (first parsed-sample-input))
(def sample-moves (last parsed-sample-input))
(def parsed-input (parse-input (slurp "input")))
(def stacks (first parsed-input))
(def moves (last parsed-input))

(deftest test-sample-input
  (testing "input parsing"
    (is (= [["N" "Z"] ["D" "C" "M"] ["P"]] sample-stacks))
    (is (= [[1 2 1] [3 1 3] [2 2 1] [1 1 2]] sample-moves)))

  (testing "apply move"
    (is (= [["D" "N" "Z"] ["C" "M"] ["P"]] (apply-move sample-stacks 2 1 1))))

  (testing "apply moves"
    (is (= [["M" "C" "D" "N" "Z"] [] ["P"]] (apply-moves sample-stacks 2 1 3))))

  (testing "i like to move it move it"
    (is (= [["C"] ["M"] ["Z" "N" "D" "P"]] (apply-all-moves sample-stacks sample-moves))))

  (testing "task1"
    (is (= "CMZ" (task1 sample-stacks sample-moves)))))

(deftest test-real-input
  (testing "task1"
    (is (= "FWNSHLDNZ" (task1 stacks moves)))))

(deftest test-task2
  (testing "sample input"
    (is (= "MCD" (task2 sample-stacks sample-moves))))

  (testing "real input"
    (is (= "RNRGDNFQG" (task2 stacks moves)))))