(ns japiirainen.aoc-2015.25
  (:require [clojure.string :as str]))

(defn input [s]
  (let [parts (str/split s #" ")]
    (letfn [(str-to-i [n s] (->> s (drop-last n) str/join read-string))]
      [(->> (nth parts 16) (str-to-i 1)) (->> (nth parts 18) (str-to-i 2))])))

(defn walk-diagonal [r c v ip]
  (if (= [r c] ip)
    v
    (let [[r c] (if (= r 1) [(inc c) 1] [(dec r) (inc c)])]
      (recur r c (mod (* v 252533) 33554393) ip))))

(->> "inputs/25.txt" slurp input (walk-diagonal 1 1 20151125) (println "Part 1:"))
