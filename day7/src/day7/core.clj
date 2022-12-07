(ns day7.core
  (:require [clojure.string :as str]))

(defn add-node [tree path attr name]
  (if (= "dir" attr)
    (assoc-in tree (conj path name) {})
    (assoc-in tree (conj path name) (Integer/parseInt attr))))

(defn update-tree [tree path input-line]
  (let [[_ new-dir] (re-matches #"\$ cd (.*)$" input-line)
        [attr name] (str/split input-line #" ")]
    (cond
      (= "$ ls" input-line) [tree path]
      (= ".." new-dir) [tree (drop-last path)]
      (seq new-dir) [tree (conj (vec path) new-dir)]
      :else [(add-node tree path attr name)
             path])))

(defn parse-input
  ([input]
   (parse-input [{} []]
                (str/split-lines input)))
  ([[tree path] input-lines]
   (if (empty? input-lines)
     tree
     (parse-input (update-tree tree
                               path
                               (first input-lines))
                  (rest input-lines)))))

(defn make-size-tree
  ([tree] (make-size-tree tree ["/"]))
  ([tree path]
   (let [node (get-in tree path)]
     (if (number? node)
       nil
       (let [files-size (reduce + (filter number? (vals node)))
             subdir-namess (->> node seq (filter #(map? (last %))) (map first))
             subdirs (map (fn [subdir] (make-size-tree tree (conj path subdir))) subdir-namess)
             node-size (+ files-size (reduce + (map :size subdirs)))]
         {:name (last path)
          :size node-size
          :children subdirs})))))

(defn filter-small-dirs [node pred]
  (concat (reduce concat
                  (map #(filter-small-dirs % pred)
                       (node :children)))
          (if (pred node)
            [node]
            nil)))

(defn task1 [size-tree]
  (reduce + (map #(% :size) (filter-small-dirs size-tree #(> 100000 (% :size))))))

(def disk-size   70000000)
(def update-size 30000000)

(defn task2 [size-tree]
  (let [lacking-space (- update-size (- disk-size (size-tree :size)))
        big-enough-dirs (filter-small-dirs size-tree #(< lacking-space (% :size)))]
    ((first (sort #(compare (%1 :size) (%2 :size)) big-enough-dirs)) :size)))

