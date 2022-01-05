(ns japiirainen.aoc-2015.23
  (:require [clojure.string :as str]))

(defn drop-last-str [str]
  (->> str drop-last str/join))

(defn parse-line [l]
  (let [[op & rest] (str/split l #" ")]
    (case op
      "jio" [:jio (keyword (drop-last-str (nth rest 0))) (read-string (nth rest 1))]
      "jie" [:jie (keyword (drop-last-str (nth rest 0))) (read-string (nth rest 1))]
      "inc" [:inc (keyword (nth rest 0))]
      "tpl" [:tpl (keyword (nth rest 0))]
      "hlf" [:hlf (keyword (nth rest 0))]
      "jmp" [:jmp (read-string (nth rest 0))])))

(def input (->> "inputs/23.txt" slurp str/split-lines (map parse-line) (apply vector)))

(defn run-instruction [ip regs [code x y]]
  (case code
    :hlf [(+ ip 1) (update regs x #(/ % 2))]
    :tpl [(+ ip 1) (update regs x #(* % 3))]
    :inc [(+ ip 1) (update regs x inc)]
    :jmp [(+ ip x) regs]
    :jie [(+ ip (if (even? (regs x)) y 1)) regs]
    :jio [(+ ip (if (= 1 (regs x)) y 1)) regs]))

(defn evalualte [instructions [ip regs]]
  (let [i (get instructions ip)]
    (if (not i)
      (regs :b)
      (recur instructions (run-instruction ip regs i)))))

(->> [0 {:a 0 :b 0}] (evalualte input) (println "Part 1:"))
(->> [0 {:a 1 :b 0}] (evalualte input) (println "Part 2:"))