(ns japiirainen.aoc-2015.22
  (:require [clojure.string :as str]
            [clojure.data.priority-map :refer [priority-map]]))

(def input (->>
            "inputs/22.txt"
            slurp
            str/split-lines
            (map #(-> % (str/split #": ") (nth 1) read-string))))

(def boss-start-hp (-> input (nth 0)))
(def boss-dmg (-> input (nth 1)))

(defn non-pos? [n] (<= n 0))

(defn apply-effects [debuf [hp boss-hp mana shield poison recharge spent]]
  [(- hp debuf)
   (- boss-hp (if (pos-int? poison) 3 0))
   (+ mana (if (pos-int? recharge) 101 0))
   (dec shield)
   (dec poison)
   (dec recharge)
   spent])

(defn boss-move [state]
  (let [[hp boss-hp mana shield poison recharge spent] (apply-effects 0 state)]
    (if (non-pos? boss-hp)
      [spent]
      [(- hp (- boss-dmg (if (pos-int? shield) 7 0))) boss-hp mana shield poison recharge spent])))

(defn next-moves [dmg state]
  (let [[hp boss-hp mana shield poison recharge spent] (apply-effects dmg state)
        moves [[hp (- boss-hp 4) (- mana 53) shield poison recharge (+ spent 53)]
               [(+ hp 2) (- boss-hp 2) (- mana 73) shield poison recharge (+ spent 73)]
               #_{:clj-kondo/ignore [:missing-else-branch]}
               (if (non-pos? shield)   [hp boss-hp (- mana 113) 6 poison recharge (+ spent 113)])
               #_{:clj-kondo/ignore [:missing-else-branch]}
               (if (non-pos? poison)   [hp boss-hp (- mana 173) shield 6 recharge (+ spent 173)])
               #_{:clj-kondo/ignore [:missing-else-branch]}
               (if (non-pos? recharge) [hp boss-hp (- mana 229) shield poison 5   (+ spent 229)])]]
    (->> moves
         (filter boolean)
         (filter #(<= 0 (first %)))
         (filter #(<= 0 (nth % 2)))
         (map boss-move)
         (filter #(<= 0 (first %))))))

(defn bfs [dmg q]
  (let [[state _] (peek q) q (pop q)]
    (if (= 1 (count state))
      (first state)
      (->> state
           (next-moves dmg)
           (map (fn [state] [state (last state)]))
           (apply conj q)
           (recur dmg)))))

(->> (priority-map [50 boss-start-hp 500 0 0 0 0] 0) (bfs 0) (println "Part 1:"))
(->> (priority-map [49 boss-start-hp 500 0 0 0 0] 0) (bfs 1) (println "Part 1:"))