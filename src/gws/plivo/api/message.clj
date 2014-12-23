(ns gws.plivo.api.message
  (:refer-clojure :exclude [get list])
  (:require [gws.plivo.client :as client]))

(defn base-url
  [client]
  (format client/base-url-fmt
          (format "Account/%s/Message/" (:auth-id client))))

(defn send!
  "https://www.plivo.com/docs/api/message/#send-a-message"
  [client message]
  (let [url (base-url client)]
    (client/request client :post url message)))

(defn get
  "https://www.plivo.com/docs/api/message/#get-details-of-a-single-message"
  [client id]
  (let [url (str (base-url client) id "/")]
    (client/request client :get url)))

(defn list
  "https://www.plivo.com/docs/api/message/#get-details-of-all-messages"
  ([client]
   (list client {}))
  ([client params]
   (let [url (base-url client)]
     (client/request client :get url params))))
