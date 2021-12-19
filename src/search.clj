(ns search
  (:require [clojure.java.io :as io]
            [clojure.data.xml :as xml]
            [clojure.string :as str]))

(defn get-children [element tag]
  (->> element
       :content
       (filter (fn [e] (and (xml/element? e) (= (:tag e) tag))))))

(defn get-child [element tag]
  (first (get-children element tag)))

(defn get-str [element tag]
  (->> (get-child element tag)
       :content
       first))

(defn load-docs [path]
  (with-open [w (io/reader path)]
    (let [feed (xml/parse w)
          docs (get-children feed :doc)]
      (doall
       (map-indexed (fn [id doc]
                      {:id id
                       :title (get-str doc :title)
                       :url (get-str doc :url)
                       :text (get-str doc :abstract)})
                    docs)))))

(defn search-loop-substring [docs term]
  (doall
   (filter (fn [{:keys [text]}] (and text (str/includes? text term)))
           docs)))

(defn search-loop-regex [docs term]
  (let [regex (re-pattern (str "(?i)\\b" term "\\b"))]
    (doall
     (filter (fn [{:keys [text]}]
               (and text (re-find regex text)))
             docs))))
