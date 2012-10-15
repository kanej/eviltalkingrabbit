(ns eviltalkingrabbit.rabbit-api
  (:require [clj-http.client :as client]))

(defn speak [rabbit-serial phrase]
  (let [escaped-phrase (.replace phrase " " "+")
        url (str "http://www.nabaztaglives.com/api.php?sn=" rabbit-serial "&tts=" escaped-phrase)
        response (client/get url)]
    (println response)
    (if (= 200 (:status response)) :success :failure)))
