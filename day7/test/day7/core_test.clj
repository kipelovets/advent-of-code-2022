(ns day7.core-test
  (:require [clojure.test :refer :all]
            [day7.core :refer :all]))

(def sample-input "$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k")

(def tree {"/" {"var" {}}})

(deftest test-update-tree
  (testing "cd"
    (is (= [tree ["/"]] (update-tree tree [] "$ cd /")))
    (is (= [tree ["/"]] (update-tree tree ["/" "var"] "$ cd ..")))
    (is (= [tree ["/" "var"]] (update-tree tree ["/"] "$ cd var")))
    (is (= [{"/" {"var" {"lib" {}}}} ["/" "var"]] (update-tree tree ["/" "var"] "dir lib")))
    (is (= [{"/" {"var" {"a.txt" 123}}} ["/" "var"]] (update-tree tree ["/" "var"] "123 a.txt")))))

(def sample-tree {"/" {"a" {"e" {"i" 584},
                            "f" 29116,
                            "g" 2557,
                            "h.lst" 62596},
                       "b.txt" 14848514,
                       "c.dat" 8504156,
                       "d" {"d.ext" 5626152,
                            "d.log" 8033020,
                            "j" 4060174,
                            "k" 7214296}}})

(deftest test-parse-input
  (testing "sample-input"
    (is (= sample-tree (parse-input sample-input)))))

(def sample-size-tree (make-size-tree sample-tree))

(deftest test-size
  (testing "sample-input"
    (is (= {:name "/" :size 1 :children []} (make-size-tree {"/" {"a" 1}})))

    (is (= {:name "/"
            :size 48381165
            :children [{:name "a"
                        :size 94853
                        :children [{:name "e"
                                    :size 584
                                    :children []}]}
                       {:name "d"
                        :size 24933642
                        :children []}]} sample-size-tree))))

(deftest test-filter-small-dirs
  (testing "sample input"
    (is (= [584 94853] (map #(% :size) (filter-small-dirs sample-size-tree #(> 100000 (% :size))))))))

(deftest test-task1
  (testing "sample input"
    (is (= 95437 (task1 (make-size-tree sample-tree)))))

  (testing "real input"
    (is (= 1989474 (task1 (make-size-tree (parse-input (slurp "input"))))))))

(deftest test-task2
  (testing "sample input"
    (is (= 24933642 (task2 sample-size-tree))))

  (testing "real input"
    (is (= 1111607 (task2 (make-size-tree (parse-input (slurp "input"))))))))



