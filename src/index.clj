(ns index
  (:require [analyze :as a]
            [clojure.set :as set]))

(defn build-index [docs]
  (->> docs
       (filter :text)
       (mapcat (fn [{:keys [id text]}]
                 (map (fn [token] {token #{id}}) (a/analyze text))))
       (apply merge-with set/union)))

(comment
  (build-index (list {:id 1 :text "A donut on a glass plate. Only the donuts."}
                     {:id 2 :text "donut is a donut"}))
  (def index (time (build-index search/docs))))
