(ns analyze
  (:require [clojure.string :as str]
            [clojure-stemmer.porter.stemmer :as stemmer]))

(def split-regex #"[^\w\d]")

(defn tokenize [text]
  (->> (str/split text split-regex)
       (filter (comp not str/blank?))))

(defn lowercase [tokens]
  (map str/lower-case tokens))

(def stopwords #{"a" "and" "be" "have" "i"
                 "in" "of" "that" "the" "to"})

(defn remove-stopwords [tokens]
  (filter (comp not stopwords) tokens))

(defn stem [tokens]
  (map stemmer/stemming tokens))

(defn analyze [text]
  (-> text
      tokenize
      lowercase
      remove-stopwords
      stem))
