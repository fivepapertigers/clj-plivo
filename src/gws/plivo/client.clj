(ns gws.plivo.client
  (:require [cheshire.core :as json]
            [clj-http.client :as http-client]))

(defrecord Client [auth-id auth-token clj-http-options])

(def base-url-fmt "https://api.plivo.com/v1/%s")
(def user-agent "clj-plivo/0.1.1 (https://github.com/gws/clj-plivo)")

(defn request
  "Calls the supplied Plivo URL and returns the decoded response body."
  ([client method url]
   (request client method url {}))
  ([client method url params]
   (let [params (merge {:as :json
                        :content-type :json
                        :basic-auth ((juxt :auth-id :auth-token) client)
                        :method method
                        :url url}
                       (if (some #{method} '(:get :delete))
                         {:query-params params}
                         {:body (json/generate-string params)})
                       (:clj-http-options client))]
     (:body (http-client/request params)))))

(defn create
  "Build a Plivo client, containing an optional map of parameters which, if
  specified, will be merged into the clj-http parameter map."
  ([api-key auth-token]
   (create api-key auth-token {}))
  ([api-key auth-token clj-http-options]
   (->Client api-key auth-token clj-http-options)))
