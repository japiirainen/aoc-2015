(ns japiirainen.aoc-2015.13
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))

(defn parse-line [l]
  (let [parts (str/split l #" ")
        dir (case (get parts 2) "gain" identity "lose" #(- 0 %))]
    [(get parts 0) (->> (get parts 3) read-string dir) (str/join "" (drop-last (get parts 10)))]))

(def input (->> "inputs/13.txt" slurp str/split-lines (map parse-line)))

(def graph (->> input (reduce (fn [m [a h b]] (assoc m [a b] h)) {})))

(def people (->> input (map first) set))

(defn happiness-change [[a b]]
  (+ (graph [a b]) (graph [b a])))

(defn happiness-2 [seats]
  (->> seats (partition 2 1) (map happiness-change) (reduce +)))

(defn happiness-1 [seats]
  (happiness-2 (conj seats (last seats))))

(defn max-happiness [f] (->> people combo/permutations (map f) (apply max)))

(->> happiness-1 max-happiness (println "Part 1:"))
(->> happiness-2 max-happiness (println "Part 1:"))