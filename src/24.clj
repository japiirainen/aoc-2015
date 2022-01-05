(ns japiirainen.aoc-2015.24
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))

(def input (->> "inputs/24.txt" slurp str/split-lines (map read-string)))

(defn valid? [target numbers len]
  (->> len
       (combo/combinations numbers)
       (filter #(= (reduce + %) target))))

(defn minimize [ns ps]
  (->> (iterate inc 1)
       (map #(valid? (/ (reduce + ns) ps) ns %))
       (filter not-empty)
       first
       (map #(reduce * %))
       (apply min)))

(->> 3 (minimize input) (println "Part 1:"))
(->> 4 (minimize input) (println "Part 2:"))
