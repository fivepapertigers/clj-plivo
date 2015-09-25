(ns gws.plivo.api.call
  (:refer-clojure :exclude [get list])
  (:require [gws.plivo.client :as client]))

(defn base-url
  [client]
  (format client/base-url-fmt
          (format "Account/%s/Call/" (:auth-id client))))

(defn create!
  "https://www.plivo.com/docs/api/call/#make-an-outbound-call"
  [client call_options]
  (let [url (base-url client)]
    (client/request client :post url call_options)))

(defn get
  "https://www.plivo.com/docs/api/call/#get-call-detail-record-of-a-call"
  ([client id]
    (get client id {}))
  ([client id params]
    (let [url (str (base-url client) id "/")]
      (client/request client :get url params))))

(defn list
  "https://www.plivo.com/docs/api/call/#get-all-call-details"
  ([client]
   (list client {}))
  ([client params]
   (let [url (base-url client)]
    (client/request client :get url params))))


(defn get-live
  "https://www.plivo.com/docs/api/call/#get-details-of-a-live-call"
  [client id]
    (get client id {:status "live"}))

(defn list-live
  "https://www.plivo.com/docs/api/call/#get-all-live-calls"
  [client]
  (list client {:status "live"}))

(defn hangup!
  "https://www.plivo.com/docs/api/call/#hangup-a-specific-call"
  ([client id]
    (let [url (str (base-url client) id "/")]
      (client/request client :delete url))))

(defn transfer!
  "https://www.plivo.com/docs/api/call/#transfer-a-call"
  ([client id]
    (transfer! client id {}))
  ([client id transfer_options]
    (let [url (base-url client)]
      (client/request client :post url transfer_options))))
