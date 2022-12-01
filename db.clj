(ns db
  (:require [clojure.string :as str])
  (:require [clojure.edn :as edn]))



(defn add-brackets [value]
  (str "{\"" (str/join "\" \"" value) "\"}"))

(defn print-dict [dict]
  (doseq [[k,value] dict]
  (println k ":" (add-brackets value))))


(defn list-to-dict [list-elements]
 (def k (map #(edn/read-string (first %)) list-elements))
          (def values
            (for [item list-elements] (drop 1 item)))
          (def dict (zipmap k values))
          (println)
          (print-dict  dict )
          dict
)  

(defn read-file-customer [filename]
  (let [file-data (slurp filename)]
    (def file-data-split (str/split-lines file-data)))
  (def elements
    (for [line file-data-split]
      (str/split line #"\|")))

    (list-to-dict elements)

  )

(defn read-file-product [filename]
  (let [file-data (slurp filename)]
    (def file-data-split (str/split-lines file-data)))

  (def entries
    (for [line file-data-split]
      (str/split line #"\|")))

      (list-to-dict entries)
)

(defn read-file-sale [filename]
  (let [file-data (slurp filename)]
    (def file-data-split (str/split-lines file-data)))

    
)