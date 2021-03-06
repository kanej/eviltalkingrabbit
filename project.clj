(defproject eviltalkingrabbit "0.1.0-SNAPSHOT"
  :description "Make the evil rabbit talk."
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.1"]
                 [ring/ring-jetty-adapter "1.1.6"]
                 [hiccup "1.0.1"]
                 [hiccup-bootstrap "0.1.1"]
                 [com.cemerick/friend "0.1.0"]
                 [friend-oauth2 "0.0.1"]
                 [clj-http "0.5.3"]]
  :main eviltalkingrabbit.handler
  :plugins [[lein-ring "0.7.3"]]
  :ring {:handler eviltalkingrabbit.handler/app}
  :min-lein-version "2.0.0"
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}})
