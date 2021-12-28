(ns japiirainen.aoc-2015.05
  (:require [clojure.string :refer [split includes?]]))

(defn lines [s]
  (split s #"\n"))

(defn valid1? [s]
  (and
   (not (some #(includes? s (str %)) '("ab" "cd" "pq" "xy")))
   (->> s (partition 2 1) (some #(apply = %)))
   (->> s (filter #(includes? "aeiou" (str %))) count (< 2))))

(defn valid2? [s]
  (letfn [(double-pairs? [[p & ps]]
            (and ps (or (some #{p} (rest ps)) (recur ps))))]
    (and
     (->> s (partition 3 1) (some (fn [[a _ c]] (= a c))))
     (->> s (partition 2 1) double-pairs?))))


(->> (slurp "inputs/05.txt") lines (filter valid1?) count (println "Part 1:"))
(->> (slurp "inputs/05.txt") lines (filter valid2?) count (println "Part 2:"))