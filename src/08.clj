(ns japiirainen.aoc-2015.08
  (:require [clojure.string :as str]))

(def lines (->> (slurp "inputs/08.txt") str/split-lines))

(defn p1
  ([c [a & s]]
   (if (not a)
     c
     (let [n (cond
               (not= \\ a) 0
               (= (first s) \x) 3
               :else 1)]
       (recur (inc c) (drop n s)))))
  ([s] (p1 -2 s)))

(defn p2 [s]
  (letfn [(isExpand [x] (str/includes? "\\\"" (str x)))]
    (+ 2 (count (filter isExpand s)))))

(->> lines (map p1) (reduce +) (- (->> lines (map count) (reduce +))) (prn "Part 1:"))
(->> lines (map p2) (reduce +) (prn "Part 1:"))