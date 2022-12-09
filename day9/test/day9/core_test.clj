(ns day9.core-test
  (:require [clojure.test :refer :all]
            [day9.core :refer :all]
            [clojure.string :as str]))

(deftest test-touching
  (testing "sample data"
    (is (= true (touching? [0 0] [0 0])))
    (is (= true (touching? [0 0] [1 1])))
    (is (= true (touching? [0 0] [-1 1])))
    (is (= false (touching? [10 10] [-10 10])))
    (is (= false (touching? [10 10] [12 10])))))

(deftest test-move-closer
  (testing "sample data"
    (is (= [1 2] (move-closer [1 3] [1 1])))
    (is (= [2 1] (move-closer [3 1] [1 1])))
    (is (= [2 2] (move-closer [1 2] [3 1])))
    (is (= [2 2] (move-closer [2 3] [3 1])))))

(deftest test-follow-direction
  (testing "sample data"
    (is (= [[[4 0] [4 0] [4 1] [4 2] [4 3]] [[4 4] [4 3]]] (follow-direction [4 0] [4 0] "R" 4)))
    (is (= [[[4 0] [4 1]] [[4 2] [4 1]]] (follow-direction [4 1] [4 0] "R" 1)))))

(def sample-data "R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2")

(defn parse-input [data]
  (->> data
       str/split-lines
       (map (fn [row]
              (let [[dir steps] (str/split row #" ")]
                [dir (Integer/parseInt steps)])))))

(def sample-input (parse-input sample-data))
(def real-input (parse-input (slurp "input")))

(deftest test-follow-motions
  (testing "sample data"
    (is (= [[4 0] [4 1] [4 1]] (follow-motions [[4 1] [4 0]] [["R" 1]])))))

(deftest test-task1
  (testing "sample data"
    (is (= 13 (task1 sample-input))))

  (testing "real data"
    (is (= 6037 (task1 real-input)))))

(deftest test-move-knots
  (testing "sample data"
    (is (= [[0 -1] [0 0] [0 1] [0 2]] (move-knots [[0 0] [0 1] [0 2] [0 3]] "L")))))

(def larger-sample-input 
  (parse-input "R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20"))

(deftest test-task2
  (testing "first sample data"
    (is (= 1 (task2 sample-input))))
  
  (testing "larger sample data"
    (is (= 36 (task2 larger-sample-input))))
  
  (testing "real data"
    (is (= 2485 (task2 real-input)))))