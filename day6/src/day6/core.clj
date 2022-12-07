(ns day6.core)

(defn find-unique-subs [buffer len]
  (loop [ind 0]
    (if (> ind (+ (count buffer) len))
      nil
      (let [chars (subs buffer ind (+ ind len))]
        (if (= len (count (set chars)))
          (+ len ind)
          (recur (inc ind)))))))

(def marker-len 4)

(defn find-marker [buffer]
  (find-unique-subs buffer marker-len))

(def message-len 14)

(defn find-message [buffer]
  (find-unique-subs buffer message-len))