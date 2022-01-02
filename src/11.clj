(ns japiirainen.aoc-2015.11
  (:require [clojure.string :as str]))

(defn next-pw [pw]
  (case (last pw)
    \z (str (->> pw drop-last next-pw) \a)
    ; skip i o l
    \h (str (->> pw drop-last (str/join "")) \j)
    \n (str (->> pw drop-last (str/join "")) \p)
    \k (str (->> pw drop-last (str/join "")) \m)
    (str (->> pw drop-last (str/join "")) (->> pw last int inc char))))

(defn increasing-triple? [triple]
  (let [[a b c] (map int triple)]
    (= (inc a) b (dec c))))

(defn has-two-pairs? [[[a b] & pairs]]
  (and
   a
   (or
    (and (= a b) (some (fn [[c d]] (and (= c d) (not= a c))) (rest pairs)))
    (recur pairs))))

(defn valid-pw [pw]
  (and
   (->> pw (partition 3 1) (some increasing-triple?))
   (->> pw (partition 2 1) has-two-pairs?)))

(defn find-valid [start]
  (->> start (iterate next-pw) rest (filter valid-pw) first))

(let [[_ p1 p2] (->> (slurp "inputs/11.txt") (iterate find-valid) (take 3))]
  (println "Part 1" p1)
  (println "Part 2" p2))
