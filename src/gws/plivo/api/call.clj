(ns gws.plivo.api.call
  (:refer-clojure :exclude [get list])
  (:require [gws.plivo.client :as client]))

(defn base-url
  [client]
  (format client/base-url-fmt
          (format "Account/%s/Call/" (:auth-id client))))

(defn make-outbound!
  "https://www.plivo.com/docs/api/call/#make-an-outbound-call"
  [client call_options]
  (let [url (base-url client)]
    (client/request client :post url call_options)))