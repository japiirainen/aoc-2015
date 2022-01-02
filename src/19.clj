(ns japiirainen.aoc-2015.18
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [clojure.data.priority-map :refer [priority-map]]))

(def raw (->> "inputs/19.txt" slurp))
(def input (-> raw (str/split #"\n\n") (nth 0)))
(def molecule (-> raw (str/split #"\n\n") (nth 1)))
(def formulas (->> input str/split-lines (map #(str/split % #" => "))))
(def reverse-formulas (->> formulas (map reverse)))

(defn molecules
  ([a b pre s res]
   (if (empty? s)
     res
     (let [res
           (if (str/starts-with? s a)
             (conj res (str pre b (subs s (count a))))
             res)]
       (recur a b (str pre (first s)) (subs s 1) res))))
  ([s [a b]] (molecules a b "" s #{})))

(defn next-molecules [fs s]
  (->> fs (map #(molecules s %)) (apply set/union)))

(defn bfs [q]
  (let [[[s n] _] (peek q) q (pop q)]
    (if (= s "e")
      n
      (->> s
           (next-molecules reverse-formulas)
           (map vector (repeat (inc n)))
           (map (fn [[n s]] [[s n] (count s)]))
           (apply conj q)
           recur))))

(->> molecule (next-molecules formulas) count (println "Part 1:"))
(->> (priority-map [molecule 0] 0) bfs (println "Part 2:"))