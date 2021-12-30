(ns japiirainen.aoc-2015.10)

(def input (slurp "inputs/10.txt"))

(defn expand
  ([res [c & s]]
   (if (not c)
     (->> res reverse (apply str))
     (if (not s)
       (recur (conj res "1" c) s)
       (let [[same rest] (split-with #(= c %) s)]
         (recur (conj res (inc (count same)) c) rest)))))
  ([s] (expand nil s)))

(defn n-times [n input] (nth (iterate expand input) n))

(->> input (n-times 40) count (prn "Part 1:"))
(->> input (n-times 50) count (prn "Part 1:"))