(ns app
 (:require [db])
 (:require [menu]))

 (def custDB (db/read-file-customer "cust.txt"))
 (def prodDB (db/read-file-product "prod.txt"))
 (def salesDB (db/read-file-sale "sales.txt"))

(defn exit-program []
  (println "Good bye!")
  (println "Program is terminating...")
  (System/exit 0))

(def saleID (for [[k v] salesDB] k))
(def customerID (for [[k v] salesDB] (first v)))
(def productID (for [[k v] salesDB] (second v)))
(def productCount (for [[k v] salesDB] (last v)))
(def productPrice (for [[k v] prodDB] (last v)))
(def customerName (for [id customerID] (first (get custDB (Integer/parseInt id)))))
(def productName (for [id productID] (first (get prodDB (Integer/parseInt id)))))

(defn print-sales-report []
  (def sales-report (zipmap saleID (map vector customerName productName productCount)))

(db/print-dict sales-report)
sales-report
)

;ask the user to enter the customer name, convert it to id and search in the salesDB
  



(defn call-function [choice]
  (cond
    (= choice 1) (db/print-dict custDB)
    (= choice 2) (db/print-dict prodDB)
    (= choice 3) (print-sales-report)
    (= choice 4) (search-customer)
    ;; (= choice 5) (db/total-sales-for-product)
    (= choice 6) (exit-program)
    :else (println "\nPlease enter a valid choice!")))

(defn loop-menu []
  (menu/display-menu)
  (def choice (read-line))
  (call-function (Integer/parseInt choice))
  (if (not (= choice 6))
    (do
      (menu/press-enter-to-continue)
      (loop-menu))))


(defn start []
  (loop-menu))

(start)  