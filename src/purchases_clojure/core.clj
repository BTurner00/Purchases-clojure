(ns purchases-clojure.core
  (:require [clojure.string :as str])
  (:gen-class))

(defn -main []
  (let [purchases (slurp "purchases.csv")
        purchases (str/split-lines purchases)
        purchases (map #(str/split % #",") purchases)
        header (first purchases)
        purchases (rest purchases)
        purchases (map #(zipmap header %)
                    purchases)
        _ (println "Enter a category:  Furniture, Alcohol, Toiletries, Shoes, Food, Jewelry")
        
        input (read-line)
        purchases (filter #(= input (get % "category"))
                       purchases)
        text (pr-str purchases)]
        
        
        (spit "filtered_purchases.edn" text)))
     
  
  