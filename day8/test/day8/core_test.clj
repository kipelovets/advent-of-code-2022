(ns day8.core-test
  (:require [clojure.test :refer :all]
            [day8.core :refer :all]
            [clojure.string :as str]))

(deftest test-visible?
  (testing "edges"
    (is (= true (visible? sample-forest 0 0)))
    (is (= true (visible? sample-forest 1 0)))
    (is (= true (visible? sample-forest 4 0)))
    (is (= true (visible? sample-forest 0 4))))

  (testing "inner trees"
    (is (= true (visible? sample-forest 1 1)))
    (is (= true (visible? sample-forest 1 2)))
    (is (= false (visible? sample-forest 1 3)))))

(deftest test-task1
  (testing "sample input"
    (is (= 21 (task1 sample-forest))))

  (testing "real input"
    (is (= 1823 (task1 real-forest)))))

(deftest test-scenic-score
  (testing "sample input"
    (is (= 4 (scenic-score sample-forest 1 2)))
    (is (= 8 (scenic-score sample-forest 3 2)))))

(deftest test-task2
  (testing "sample input"
    (is (= 8 (task2 sample-forest))))

  (testing "real inpuit"
    (is (= 211680 (task2 real-forest)))))