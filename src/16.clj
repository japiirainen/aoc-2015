(ns japiirainen.aoc-2015.16
  (:require [clojure.string :as str]))

(defn split [re s] (str/split s re))
(defn split-first [re s] (str/split s re 2))
(defn find-first [f coll] (->> coll (filter f) first first))

(defn parse-things [things]
  (->> things
       (split #", ")
       (map #(split #": " %))
       (reduce (fn [m [thing amt]] (assoc m thing (read-string amt))) {})))

(defn parse-line [l]
  (->> l
       (split-first #": ")
       ((fn [[id things]] [(->> id (split #" ") last)
                           (parse-things things)]))))

(def input (->> "inputs/16.txt" slurp str/split-lines (map parse-line)))

(def valid-things
  {"children" 3
   "cats" 7
   "samoyeds" 2
   "pomeranians" 3
   "akitas" 0
   "vizslas" 0
   "goldfish" 5
   "trees" 3
   "cars" 2
   "perfumes" 1})

(defn valid-p1 [[_ things]]
  (->> things keys (every? #(= (things %) (valid-things %)))))

(defn actually-valid [[_ things]]
  (and
   (< (valid-things "cats") (get things "cats" 100))
   (< (valid-things "trees") (get things "trees" 100))
   (> (valid-things "pomeranians") (get things "pomeranians" 0))
   (> (valid-things "goldfish") (get things "goldfish" 0))
   (valid-p1 ["" (dissoc things "cats" "trees" "pomeranians" "goldfish")])))

(->> input (find-first valid-p1) (prn "Part 1:"))
(->> input (find-first actually-valid) (prn "Part 2:"))