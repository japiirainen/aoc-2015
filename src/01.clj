(ns japiirainen.aoc-2015.01
  (:require [clojure.string :refer [trim]]))

(def input (->> (slurp "inputs/01.txt") trim))

(defn find-basement [input]
  (letfn [(go [n [c & rest]]
              (if (= n -1)
                (- (count input) (inc (count rest)))
                (case c
                  \( (recur (inc n) rest)
                  \) (recur (dec n) rest))))]
    (go 0 input)))

(->> input frequencies vals (apply -) (println "Part 1:"))
(->> input find-basement (println "Part 2:"))