(ns app
 (:require [db])
 (:require [menu]))

;function that prints Good bye! and exists the program by printing Program is terminating... and then exiting the program
(defn exit-program []
  (println "Good bye!")
  (println "Program is terminating...")
  (System/exit 0))

; function that calls a specific function based on the user's choice from the menu
(defn call-function [choice]
  (cond
    (= choice 1) (db/read-file-customer "cust.txt")
    (= choice 2) (db/read-file-product "prod.txt")
    (= choice 3) (db/read-file-sale "sales.txt")
    ;; (= choice 4) (db/total-sales-for-customer)
    ;; (= choice 5) (db/total-sales-for-product)
    (= choice 6) (exit-program)
    ; print Please enter a valid choice! and then call the display-menu function
    :else (println "\nPlease enter a valid choice!")))

;function that loops the menu until the user chooses to exit
(defn loop-menu []
  (menu/display-menu)
  (def choice (read-line))
  (call-function (Integer/parseInt choice))
  (if (not (= choice 6))
    (do
      (menu/press-enter-to-continue)
      (loop-menu))))

; function that starts the application
(defn start []
  (loop-menu))

(start)  