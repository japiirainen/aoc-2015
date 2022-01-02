(ns japiirainen.aoc-2015.17
  (:require [clojure.string :as str]))

(def input (->> "inputs/17.txt" slurp str/split-lines (map read-string)))

(defn powerset [base]
  (letfn [(sets [xs x] (concat xs (map #(cons x %) xs)))]
    (reduce sets [()] base)))

(def valid (->> input powerset (filter #(= 150 (reduce + %)))))

(->> valid count (prn "Part 1:"))
(->> valid (map count) frequencies (apply min-key first) second (prn "Part 2:"))