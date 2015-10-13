(defproject org.clojars.fivepapertigers/clj-plivo "0.1.2"
  :description "A Clojure implementation of the Plivo API"
  :url "https://gitlab.com/fivepapertigers/clj-plivo"
  :min-lein-version "2.0.0"
  :license {:name "Apache 2.0"
            :url "https://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[cheshire "5.4.0"]
                 [clj-http "1.0.1"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/data.xml "0.0.8"]]
  :main gws.plivo.main)
  