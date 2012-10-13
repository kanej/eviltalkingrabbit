(ns eviltalkingrabbit.config-loader)

(def default-config 
{
  :client-id ""
  :client-secret ""
  :callback-domain "http://localhost:3000"
  :callback-path "/github.callback"
})

(defn get-file-config [file-name]
  (when (.exists (clojure.java.io/file file-name))
    (read-string (slurp file-name))))

(defn get-env-config []
  (let [client-id (System/getenv "CLIENTID")
        client-secret (System/getenv "CLIENTSECRET")
        callback-domain (System/getenv "CALLBACKDOMAIN")
        callback-path (System/getenv "CALLBACKPATH")
        config {}]
    (when (not (nil? client-id)) (assoc config :client-id client-id))
    (when (not (nil? client-secret)) (assoc config :client-secret client-secret))
    (when (not (nil? callback-domain)) (assoc config :callback-domain callback-domain))
    (when (not (nil? callback-path)) (assoc config :callback-path callback-path))
    config))

(defn load-oauth-config []
  (let [file-config (get-file-config "config.clj")
        env-config  (get-env-config)]
    (merge default-config file-config env-config)))

