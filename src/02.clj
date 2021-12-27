(ns japiirainen.aoc-2015.02
  (:require [clojure.string :refer [split]]))

(defn parse [input]
  (let [lines (split input #"\n")]
    (map #(->> (split % #"x") (map read-string)) lines)))

(defn area [[l w h]]
  (+ (+ (* 2 l w) (* 2 w h) (* 2 h l))
     (min (* l w) (* w h) (* h l))))

(defn ribbon [[l w h]]
  (+ (* l w h) (* 2 (- (+ l w h) (max l w h)))))

(->> (slurp "inputs/02.txt") parse (map area) (reduce + 0) (println "Part 1:"))
(->> (slurp "inputs/02.txt") parse (map ribbon) (reduce + 0) (println "Part 2:"))