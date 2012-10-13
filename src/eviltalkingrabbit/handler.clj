(ns eviltalkingrabbit.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.adapter.jetty :as ring]
            [cemerick.friend :as friend]
            [hiccup.bootstrap.middleware :as bootstrap]
            [eviltalkingrabbit.config :as config]
            [eviltalkingrabbit.github :as github]
            [eviltalkingrabbit.pages :as pages]
            [eviltalkingrabbit.rabbit-api :as rabbit]))

(defroutes app-routes
  (GET "/" [] "Evil Talking Rabbit")
  (GET "/speak" request
    (friend/authorize #{::github/user} 
      (pages/main-page)))
  (POST "/speak" [phrase :as request] 
    (friend/authorize #{::github/user}
      (println (str "Passing the rabbit the phrase: " phrase))
      (let [result (rabbit/speak phrase)]
        (pages/main-page result))))
  (friend/logout (ANY "/logout" request (ring.util.response/redirect "/")))
  (route/not-found "Not Found"))

(def app
  (-> app-routes
    (github/authenticate (config/load-oauth-config))
    (bootstrap/wrap-bootstrap-resources)
    (handler/site)))

(defn start [port]
  (ring/run-jetty #'app {:port (or port 3000) :join? false}))

(defn -main []
  (let [port (System/getenv "PORT")]
    (if (nil? port)
      (start nil)
      (start (Integer. port)))))
