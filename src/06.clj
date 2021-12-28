(ns japiirainen.aoc-2015.06
  (:require [clojure.string :refer [split-lines]]))

(defn parse-line [line]
  (let [[_ op & ns] (re-find #"^(.+) (\d+),(\d+) through (\d+),(\d+)$" line)
        [x0 y0 x1 y1] (map #(Integer. %) ns)]
    (case op
      "turn on" [:on x0 y0 x1 y1]
      "turn off" [:off x0 y0 x1 y1]
      "toggle" [:toggle x0 y0 x1 y1])))

(def instructions (->> (slurp "inputs/06.txt") split-lines (map parse-line)))

(defn turn-on-p1 [^ints grid [r1 c1 r2 c2]]
  (doseq [i (range r1 (inc r2))
          k (range (+ c1 (* i 1000)) (+ (inc c2) (* i 1000)))]
    (aset grid k 1)))
(defn turn-off-p1 [^ints grid [r1 c1 r2 c2]]
  (doseq [i (range r1 (inc r2))
          k (range (+ c1 (* i 1000)) (+ (inc c2) (* i 1000)))]
    (aset grid k 0)))
(defn toggle-p1 [^ints grid [r1 c1 r2 c2]]
  (doseq [i (range r1 (inc r2))
          k (range (+ c1 (* i 1000)) (+ (inc c2) (* i 1000)))]
    (aset grid k (- 1 (aget grid k)))))

(defn part1 [grid ops]
  (doseq [[op & box] ops]
    (case op
      :on     (turn-on-p1  grid box)
      :off    (turn-off-p1 grid box)
      :toggle (toggle-p1   grid box)))
  (reduce + grid))

(defn turn-on-p2 [^ints grid [r1 c1 r2 c2]]
  (doseq [i (range r1 (inc r2))
          k (range (+ c1 (* i 1000)) (+ (inc c2) (* i 1000)))]
    (aset grid k (+ 1 (aget grid k)))))
(defn turn-off-p2 [^ints grid [r1 c1 r2 c2]]
  (doseq [i (range r1 (inc r2))
          k (range (+ c1 (* i 1000)) (+ (inc c2) (* i 1000)))]
    (aset grid k (max (+ -1 (aget grid k)) 0))))
(defn toggle-p2 [^ints grid [r1 c1 r2 c2]]
  (doseq [i (range r1 (inc r2))
          k (range (+ c1 (* i 1000)) (+ (inc c2) (* i 1000)))]
    (aset grid k (+ 2 (aget grid k)))))

(defn part2 [grid ops]
  (doseq [[op & box] ops]
    (case op
      :on     (turn-on-p2  grid box)
      :off    (turn-off-p2 grid box)
      :toggle (toggle-p2   grid box)))
  (reduce + grid))

(->> instructions (part1 (int-array (* 1000 1000))) (println "Part one:"))
(->> instructions (part2 (int-array (* 1000 1000))) (println "Part two:"))