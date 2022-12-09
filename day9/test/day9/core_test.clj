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

(deftest test-follow-head
  (testing "sample data"
    (is (= [[[4 0] [4 1] [4 2] [4 3]] [4 4] [4 3]] (follow-direction [4 0] [4 0] "R" 4)))))

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
    (is (= 13 (task1 sample-input))))
  
  (testing "real data"
    (is (= 6037 (task1 real-input)))))