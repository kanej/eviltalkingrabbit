(ns eviltalkingrabbit.github
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [cemerick.friend :as friend]
            [clj-http.client :as client]
            [friend-oauth2.workflow :as oauth2]
            [cheshire.core :as j]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])))

;; OAuth2 config
(defn- access-token-parsefn [body]
  (clojure.walk/keywordize-keys
  (reduce
   #(merge %1 (clojure.string/split (str %2) #"="))
   {} (clojure.string/split body #"&"))))

(def config-auth {:roles #{::user}})

(defn uri-config [client-config]
  {:redirect-uri {:url "https://github.com/login/oauth/authorize"
                  :query {:client_id (:client-id client-config)
                          :response_type "code"
                          :redirect_uri (str (:domain (:callback client-config)) (:path (:callback client-config)))
                          :scope "user"}}

   :access-token-uri {:url "https://github.com/login/oauth/access_token"
                      :query {:client_id (:client-id client-config)
                              :client_secret (:client-secret client-config)
                              :grant_type "authorization_code"
                              :redirect_uri (str (:domain (:callback client-config)) (:path (:callback client-config)))
                              :code ""}}})

(defn authenticate [ring-app oauth-config]
  (friend/authenticate
    ring-app
    {:allow-anon? true
     :workflows [(oauth2/workflow
                  {:client-config oauth-config
                   :uri-config (uri-config oauth-config) 
                   :access-token-parsefn access-token-parsefn
                   :config-auth config-auth})]}))
