(ns day14.core-test
  (:require [clojure.test :refer :all]
            [day14.core :refer :all]))

(defn is= [a b]
  (is (= a b)))

(deftest test-parsing
  (is= [499 1] (parse-coord "499,1"))
  (is= [[498 4] [498 6] [496 6]] (parse-line "498,4 -> 498,6 -> 496,6"))
  (is= [[[498 4] [498 6] [496 6]]
        [[503 4] [502 4] [502 9] [494 9]]] example-input)
  (is= 151 (count real-input)))

(deftest test-find-bounds
  (is= [[494 0] [503 9]] (input-bounds example-input)))

(deftest test-line-contains
  (is= false (path-contains [0 0] [498 4] [498 6]))
  (is= true (path-contains [498 4] [498 4] [498 6]))
  (is= true (path-contains [498 5] [498 4] [498 6]))
  (is= true (path-contains [498 6] [498 4] [498 6]))
  (is= false (path-contains [0 6] [498 6] [496 6]))
  (is= false (path-contains [498 7] [498 6] [496 6]))
  (is= true (path-contains [497 6] [498 6] [496 6])))

(def first-example-structure (first example-input))

(deftest test-structure-contains
  (is= false (structure-contains [0 0] first-example-structure))
  (is= true (structure-contains [498 6] first-example-structure))
  (is= true (structure-contains [498 4] first-example-structure)))

(deftest test-free
  (is= false (free? [0 0] example-input #{[0 0]}))
  (is= false (free? [498 4] example-input #{}))
  (is= true (free? [500 0] example-input #{})))

(deftest test-drop-sand
  (is= [500 8] (drop-sand sand-hole
                          #(free? % example-input #{})
                          #(not (within-bounds? % example-input)))))

(deftest test-keep-dropping-sand
  (is= 24 (task1 example-input))
  ;(is= 892 (task1 real-input))
  )

(deftest test-task2
  (is= 93 (task2 example-input))
  (is= nil (task2 real-input)))