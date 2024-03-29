(ns japiirainen.aoc-2015.18
  (:require [clojure.string :as str]))

(def input (->> "inputs/18.txt" slurp str/split-lines (str/join "")))
(def starting-grid (->> input (map #(= \# %)) (into [])))

(defn get-grid [grid [r c]]
  (and
   (<= 0 r 99)
   (<= 0 c 99)
   (get grid (+ (* r 100) c))))

(defn neighbour-indexes [r c]
  [[(dec r) (dec c)]
   [(dec r)      c]
   [(dec r) (inc c)]
   [r  (dec c)]
   [r  (inc c)]
   [(inc r) (dec c)]
   [(inc r)      c]
   [(inc r) (inc c)]])

(defn neighbours [grid r c]
  (->> (neighbour-indexes r c)
       (map #(get-grid grid %))
       (filter boolean)
       count))

(defn update-cell [grid i]
  (let [r (quot i 100)
        c (rem i 100)
        n (neighbours grid r c)]
    (case n
      3 true
      2 (get-grid grid [r c])
      false)))

(defn update-cell-p2 [grid i]
  (or
   (#{0 99 9900 9999} i)
   (update-cell grid i)))

(defn step [update-fn grid]
  (->> (range 10000)
       (map #(update-fn grid %))
       (into [])))

(defn simulate [update-fn grid]
  (->> grid
       (iterate #(step update-fn %))
       (#(nth % 100))
       (filter boolean)
       count))

(->> starting-grid (simulate update-cell) (println "Part one:"))
(->> (assoc starting-grid 9900 true) (simulate update-cell-p2) (println "Part two:"))