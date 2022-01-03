(ns japiirainen.aoc-2015.20)

(def input (->> "inputs/20.txt" slurp read-string))

(defn divisors
  ([ds i n]
   (if (> (* i i) n)
     ds
     (if (not= 0 (rem n i))
       (recur ds (inc i) n)
       (recur (conj ds i (quot n i)) (inc i) n))))
  ([n] (divisors #{} 1 n)))

(defn p1 [n] (->> n divisors (reduce +) (* 10)))

(defn p2 [n] (->> n divisors (filter #(<= (quot n %) 50)) (reduce +) (* 11)))

(defn first-house [f target]
  (->> (iterate inc 1) (filter #(> (f %) target)) first))

(->> input (first-house p1) (println "Part 1:"))
(->> input (first-house p2) (println "Part 2:"))