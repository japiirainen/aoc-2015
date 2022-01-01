(ns japiirainen.aoc-2015.14
  (:require [clojure.string :as str]))

(defn parse-line [l]
  (let [parts (str/split l #" ")]
    [(->> (get parts 3) read-string)
     (->> (get parts 6) read-string)
     (->> (get parts 13) read-string)]))

(def input (->> "inputs/14.txt" slurp str/split-lines (map parse-line)))

(defn zip [c0 c1] (map vector c0 c1))

(defn dist-after-s [t [speed active inactive]]
  (let [ss (+ (* (quot t (+ active inactive)) active)
              (min (rem t (+ active inactive)) active))] (* speed ss)))

(defn give-point [max-dist [points dist]]
  (+ points (if (= max-dist dist) 1 0)))

(defn give-points [t points input]
  (let [distances (map #(dist-after-s t %) input)]
    (->> distances (zip points) (map #(give-point (apply max distances) %)) doall)))

(defn p2
  ([t points input]
   (if (= t 2503)
     (apply max points)
     (recur (inc t) (give-points t points input) input)))
  ([input] (p2 1 (repeat (count input) 0) input)))

(->> input (map #(dist-after-s 2503 %)) (apply max) (prn "Part 1:"))
(->> input p2 (prn "Part 2:"))