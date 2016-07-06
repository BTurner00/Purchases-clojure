(ns purchases-clojure.core
  (:require [clojure.string :as str]
            [compojure.core :as c]
            [ring.adapter.jetty :as j]
            [hiccup.core :as h])
  (:gen-class))
(defn read-purchases []
  (let [purchases (slurp "purchases.csv")
        purchases (str/split-lines purchases)
        purchases (map (fn [line] (str/split line #","))
                    purchases)
        header (first purchases)
        purchases (rest purchases)
        purchases (map (fn [line] (zipmap header line))
                    purchases)]
    purchases))
       

(defn purchases-html [purchases] 
  [:html
   [:body
    [:table 
     
      (map (fn [purchase]
             [:tr
              [:td (:style "width:50%") (str(get purchase "customer_id"))]
              [:td (str(get purchase "date"))]
              [:td (str(get purchase "credit_card"))]
              [:td (str(get purchase "cvv"))]
              [:td (str(get purchase "category"))]])
              
               
                 
          purchases)]]])


(defn filter-by-category [purchases category]
  (filter (fn [purchase]
            (= category (get purchase "category")))
          purchases))
     
(c/defroutes app
  (c/GET "/:category{.*}" [category]
    (let [purchases (read-purchases)
          purchases (if (= 0 (count category))
                     purchases
                     (filter-by-category purchases category))]                      
      (h/html (purchases-html purchases))))) 


(defonce server (atom nil))

(defn -main []
  (if @server
    (.stop @server))
  (reset! server (j/run-jetty app {:port 3000 :join? false}))) 
  
  