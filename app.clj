(ns app
  (:require [db])
  (:require [menu])
  (:require [clojure.string :as str]))

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
(def customerName (for [id customerID] (first (get custDB (Integer/parseInt id)))))
(def productName (for [id productID] (first (get prodDB (Integer/parseInt id)))))

(defn print-sales-report []
  (def sales-report (zipmap saleID (map vector customerName productName productCount)))
  sales-report)

(def parsedSalesDB (print-sales-report))

(defn remove-nil [list]
  (str/join " " (remove nil? list)))

(defn get-customer-id [customer-name]
  (def customer-id (for [[k v] custDB] (if (= customer-name (first v)) k)))
  (def customer-id2 (remove-nil customer-id))
  customer-id2)


(defn get-product-id [customer-name]
  (def customer-id (get-customer-id customer-name))
  (def product-id (for [[k v] salesDB] (if (= customer-id (first v)) (second v))))
  (def product-id2 (remove nil? product-id))
  product-id2)

(defn get-total-price [customer-name]
  (def customer-id (get-customer-id customer-name))
  (println customer-id)
  (if (= customer-id "")

    (println "Customer doesn't exist in database!")

    (do
      (def product-id (get-product-id customer-name))
      (def product-price (for [id product-id] (last (get prodDB (Integer/parseInt id)))))
      (def product-count (for [[k v] salesDB] (if (= customer-id (first v)) (last v))))
      (def product-count2 (remove nil? product-count))

      (def product-price2 (map #(Float/parseFloat %) product-price))
      (def product-count3 (map #(Float/parseFloat %) product-count2))
      (def total-price (reduce + (map * product-price2 product-count3)))
      (if (= total-price 0)
        (do (println "\nNo sales for this customer")
            (println customer-name "--> Total Price: $0.00"))
        (do (println)
            (println customer-name "--> Total Price: $" (format "%.2f" total-price)))))))

(defn get-product-sales [product-name]
  (def product-id (for [[k v] prodDB] (if (= product-name (first v)) k)))
  (def product-id2 (remove-nil product-id))
  (if (= product-id2 "")
    (println "Product doesn't exist in database!")
    (do
      (def product-count (for [[k v] salesDB] (if (= product-id2 (second v)) (last v))))
      (def product-count2 (remove nil? product-count))
      (def product-count3 (map #(Integer/parseInt %) product-count2))
      (def total-sales (reduce + product-count3))
      (if (= total-sales 0)
        (do (println "\nNo sales for this product")
            (println product-name "--> 0"))
        (do (println)
            (println product-name "-->" total-sales))))))

(defn call-function [choice]
  (cond
    (= choice 1) (db/print-dict custDB)
    (= choice 2) (db/print-dict prodDB)
    (= choice 3) (db/print-dict parsedSalesDB)
    (= choice 4) (do  (println "\nEnter customer name: ")
                      (flush)
                      (def customer-name (read-line))
                      (get-total-price customer-name))
    (= choice 5) (do  (println "\nEnter product name: ")
                      (flush)
                      (def product-name (read-line))
                      (get-product-sales  product-name))
    (= choice 6) (exit-program)
    :else (println "\nPlease enter a valid choice!")))

(defn run-program []
  (menu/main-menu)
  (flush)
  (def choice (read-line))
  (call-function (Integer/parseInt choice))
  (if (not (= choice 6))
    (do
      (menu/press-enter-to-continue)
      (run-program))))


(defn -main []
  (run-program))

(-main)