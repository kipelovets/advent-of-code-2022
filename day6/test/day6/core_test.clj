(ns day6.core-test
  (:require [clojure.test :refer :all]
            [day6.core :refer :all]))

(deftest test-find-marker
  (testing "sample input"
    (is (= 7 (find-marker "mjqjpqmgbljsphdztnvjfqwrcgsmlb")))
    (is (= 5 (find-marker "bvwbjplbgvbhsrlpgdmjqwftvncz")))
    (is (= 6 (find-marker "nppdvjthqldpwncqszvftbrmjlhg")))
    (is (= 10 (find-marker "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")))
    (is (= 11 (find-marker "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))))

  (testing "real input"
    (is (= 1640 (find-marker (slurp "input"))))))