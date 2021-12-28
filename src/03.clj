(ns japiirainen.aoc-2015.03)

(def input (slurp "inputs/03.txt"))

(defn move [[x y] dir]
  (case dir
    \^ [x (inc y)]
    \< [(dec x) y]
    \> [(inc x) y]
    \v [x (dec y)]))

(defn unique-houses
  ([pos vis [dir & rest]]
   (if (not dir)
     (count vis)
     (let [pos (move pos dir)]
       (recur pos (conj vis pos) rest))))
  ([moves] (unique-houses [0 0] #{} moves)))

(defn with-robot
  ([p1 p2 vis [d1 d2 & rest]]
   (if (not d1)
     (count vis)
     (let [p1 (move p1 d1) p2 (move p2 d2)]
       (recur p1 p2 (conj vis p1 p2) rest))))
  ([moves] (with-robot [0 0] [0 0] #{} moves)))

(->> input unique-houses (println "Part 1:"))
(->> input with-robot (println "Part 2:"))