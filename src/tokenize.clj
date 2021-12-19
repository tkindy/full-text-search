(ns tokenize
  (:require [clojure.string :as str]))

(def split-regex #"[^\w\d]")

(defn tokenize [text]
  (->> (str/split text split-regex)
       (filter (comp not str/blank?))))
