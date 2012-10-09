(ns eviltalkingrabbit.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [cemerick.friend :as friend]
            [eviltalkingrabbit.github :as github]))

(def oauth-config
  {:client-id "2eff2815b4b2de698d65"
   :client-secret "89e2a9e1532c739d45667659aaef7aa64b5be544"
   :callback {:domain "http://localhost:3000" :path "/github.callback"}})

(defroutes app-routes
  (GET "/" [] "Evil Talking Rabbit")
  (GET "/talk" request
       (friend/authorize #{::github/user} "What should I say?"))
  (friend/logout (ANY "/logout" request (ring.util.response/redirect "/")))
  (route/not-found "Not Found"))

(def app
  (handler/site
    (github/authenticate app-routes oauth-config)))
