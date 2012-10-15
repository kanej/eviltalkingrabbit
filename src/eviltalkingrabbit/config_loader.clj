(ns eviltalkingrabbit.config-loader)

(def default-config 
{
  :client-id ""
  :client-secret ""
  :callback-domain "http://localhost:3000"
  :callback-path "/github.callback"
  :rabbit-serial ""
})

(defn get-file-config [file-name]
  (when (.exists (clojure.java.io/file file-name))
    (read-string (slurp file-name))))

(defn conditionally-assoc [config id value]
  (if (not (nil? value))
    (assoc config id value)
    config))

(defn get-env-config []
  (let [client-id (System/getenv "CLIENTID")
        client-secret (System/getenv "CLIENTSECRET")
        callback-domain (System/getenv "CALLBACKDOMAIN")
        callback-path (System/getenv "CALLBACKPATH")
        rabbit-serial (System/getenv "RABBITSERIAL")
        config {}]
    (-> config
      (conditionally-assoc :client-id client-id)
      (conditionally-assoc :client-secret client-secret)
      (conditionally-assoc :callback-domain callback-domain)
      (conditionally-assoc :callback-path callback-path)
      (conditionally-assoc :rabbit-serial rabbit-serial)
      )))

(defn load-oauth-config []
  (let [file-config (get-file-config "config.clj")
        env-config  (get-env-config)]
    (merge default-config file-config env-config)))

