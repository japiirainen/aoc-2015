(ns japiirainen.aoc-2015.12
  (:require [cheshire.core :as cc]))

(def JSON (->> "inputs/12.txt" slurp cc/parse-string))

(defn sum-json-1 [o]
  (cond
    (map? o) (->> o vals (map sum-json-1) (reduce +))
    (vector? o) (->> o (map sum-json-1) (reduce +))
    (integer? o) o
    :else 0))

(defn sum-json-2 [o]
  (cond
    (map? o)
    (if (->> o vals (some #(= "red" %)))
      0
      (->> o vals (map sum-json-2) (reduce +)))
    (vector? o) (->> o (map sum-json-2) (reduce +))
    (integer? o) o
    :else 0))

(->> JSON sum-json-1 (println "Part 1:"))
(->> JSON sum-json-2 (println "Part 2:"))