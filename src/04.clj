(ns japiirainen.aoc-2015.04
  (:require [clojure.string :refer [trim]]))
(import 'java.security.MessageDigest
        'java.math.BigInteger)

(defn md5 [s]
  (->> s .getBytes
       (.digest (MessageDigest/getInstance "MD5"))
       (BigInteger. 1)))

; This is not in the std lib??
(defn find-first [predicate xs]
  (->> xs (filter predicate) first))

(defn find-hash [input n]
  (->> (iterate inc 0)
       (find-first #(->> % (str input) md5 (> n)))))

(->>
 (BigInteger. "000001000000000000000000000000000" 16)
 (find-hash (->> (slurp "inputs/04.txt") trim))
 (println "Part 1:"))

(->>
 (BigInteger. "000000100000000000000000000000000" 16)
 (find-hash (->> (slurp "inputs/04.txt") trim))
 (println "Part 2:"))