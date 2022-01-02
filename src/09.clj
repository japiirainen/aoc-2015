(ns japiirainen.aoc-2015.09
  (:require [clojure.math.combinatorics :as combo]
            [clojure.string :as str]))

(defn parse-line [l]
  (let [[l1 rest] (str/split l #" to ")
        [l2 dist] (str/split rest #" = ")]
    [l1 l2 (read-string dist)]))

(def input (->> (slurp "inputs/09.txt") str/split-lines (map parse-line)))

(defn add-edge [g [a b d]]
  (assoc g [a b] d [b a] d))

(def graph (reduce add-edge {} input))

(def cities (->> graph keys (map first) set))

(defn distance [cities]
  (->> cities (partition 2 1) (map graph) (reduce +)))

(defn find-dist [f]
  (->> cities combo/permutations (map distance) (apply f)))

(->> min find-dist (println "Part 1:"))
(->> max find-dist (println "Part 1:"))