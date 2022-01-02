(ns japiirainen.aoc-2015.15
  (:require [clojure.string :as str]))

(defn drop-last-str [str]
  (->> str drop-last str/join))

(defn parse-line [l]
  (let [parts (str/split l #" ")]
    (letfn [(get-part [i] (->> (get parts i) drop-last-str read-string))]
      [(get-part 2) (get-part 4) (get-part 6) (get-part 8)
       (->> (get parts 10) read-string)])))

(def input (->> "inputs/15.txt" slurp str/split-lines (map parse-line)))

(defn zip [c0 c1] (map vector c0 c1))

(defn score [parts input]
  (let [scores (->> parts
                    (zip input)
                    (map (fn [[ingredient spoons]]
                           (map #(* spoons %) ingredient))))]
    (->> (range 4)
         (map (fn [i] (->> scores (map #(nth % i)) (reduce +))))
         (map #(max 0 %))
         (reduce *))))

(defn cal-score [parts input]
  (->> parts
       (zip input)
       (map (fn [[ingredients spoons]] (* (nth ingredients 4) spoons)))
       (reduce +)
       (#(if (= % 500) (score parts input) -1))))

(defn max-score [f input]
  (apply max
         (for [i (range 100)
               j (range (- 100 i))
               k (range (- 100 i j))]
           (f [i j k (- 100 i j k)] input))))

(->> input (max-score score) (println "Part 1:"))
(->> input (max-score cal-score) (println "Part 2:"))