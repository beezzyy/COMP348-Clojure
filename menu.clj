(ns menu)

(defn main-menu []
  (println "\n*** Sales Menu ***")
  (println "-------------------")
  (println "1. Display Customer Table   ")
  (println "2. Display Product Table    ")
  (println "3. Display Sales Table      ")
  (println "4. Total Sales for Customer ")
  (println "5. Total Sales for Product  ")
  (println "6. Exit                     ")
  (println "\nEnter an option?"))

(defn press-enter-to-continue []
  (println "\nPress Enter to continue...")
  (read-line))
