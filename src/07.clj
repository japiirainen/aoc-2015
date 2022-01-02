(ns japiirainen.aoc-2015.07
  (:require [clojure.string :as str]))

(defn parse-gate [l]
  (let [[op res] (str/split l #" -> ")]
    (cond
      (str/includes? op "AND")    [res :and   (str/split op #" AND ")]
      (str/includes? op "OR")     [res :or    (str/split op #" OR ")]
      (str/includes? op "LSHIFT") [res :shl   (str/split op #" LSHIFT ")]
      (str/includes? op "RSHIFT") [res :shr   (str/split op #" RSHIFT ")]
      (str/includes? op "NOT")    [res :not   (subs op 4)]
      :else                       [res :const op])))

(def insts
  (->> (slurp "inputs/07.txt")
       str/split-lines
       (map parse-gate)
       (reduce (fn [m [res & op]] (assoc m res op)) {})))

(defn number-str? [s]
  (and (string? s) (every? #(str/includes? "0123456789" (str %)) s)))

(defn apply-gates [gate-fn [op args]]
  (case op
    :and   (->> args (map gate-fn) (apply bit-and))
    :or    (->> args (map gate-fn) (apply bit-or))
    :shl   (->> args (map gate-fn) (apply bit-shift-left))
    :shr   (->> args (map gate-fn) (apply bit-shift-right))
    :not   (->> args gate-fn bit-not)
    :const (->> args gate-fn)))

(def v1 (memoize #(if (number-str? %)
                    (read-string %)
                    (apply-gates v1 (insts %)))))

(def v2 (memoize
         #(if (= "b" %)
            (v1 "a")
            (if (number-str? %)
              (read-string %)
              (apply-gates v2 (insts %))))))

(->> "a" v1 (println "Part one:"))
(->> "a" v2 (println "Part two:"))