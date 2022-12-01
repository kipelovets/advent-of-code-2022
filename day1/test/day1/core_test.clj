(ns day1.core-test
  (:require [clojure.test :refer :all]
            [day1.core :refer :all]))

(deftest test-data
  (def test-input "1000
2000
3000

4000

5000
6000

7000
8000
9000

10000")

  (testing "reading calories"
    (is (= [6000 4000 11000 24000 10000] (read-calories-per-elf test-input))))

  (testing "test input"
    (is (= 24000 (fattest-elf (read-calories-per-elf test-input)))))
  
  (testing "test part 2"
    (is (= 45000 (top-three (read-calories-per-elf test-input))))))

(deftest real-data
  (testing "part1"
    (is (= 69310 (fattest-elf (read-calories-per-elf (slurp "input"))))))
  
  (testing "part2" 
    (is (= 206104 (top-three (read-calories-per-elf (slurp "input")))))))