(ns purchases-clojure.core
  (:require [clojure.string :as str])
  (:gen-class))

(defn -main []
  (println "Enter a category:  Furniture, Alcohol, Toiletries, Shoes, Food, Jewelry")
  (let [people (slurp "purchases.csv")
        people (str/split-lines people)
        people (map (fn [line] (str/split line #","))
                 people)
        header (first people)
        people (rest people)
        people (map (fn [line] (zipmap header line))
                 people)
        
        
        input (read-line)
        people (filter (fn [line] (= input (get line "category")))
                       people)]
    people))
  