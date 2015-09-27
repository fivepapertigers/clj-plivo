(ns gws.plivo.xml
  (:require [clojure.data.xml :as xml]))

(def allowed-conference-options [:muted :enterSound :exitSound :startConferenceOnEnter :endConferenceOnExit :stayAlone :waitSound :maxMembers :record :recordFileFormat :timeLimit :hangupOnStar :action :method :callbackUrl :callbackMethod :digitsMatch :floorEvent :redirect :transcriptionType :transcriptionUrl :transcriptionMethod :relayDTMF])
(def allowed-dial-options [:action :method :hangupOnStar :timeLimit :timeout :callerId :callerName :confirmSound :confirmKey :dialMusic :callbackUrl :callbackMethod :redirect :digitsMatch :digitsMatchBLeg :sipHeaders])
(def allowed-number-options [:sendDigits :sendOnPreanswer])
(def allowed-user-options [:sendDigits :sendOnPreanswer :sipHeaders])
(def allowed-dtmf-options [:async])
(def allowed-get-digits-options [:action :method :timeout :digitTimeout :finishOnKey :numDigits :retries :redirect :playBeep :validDigits :invalidDigitsSound :log])
(def allowed-hangup-options [:reason :schedule])
(def allowed-message-options [:src :dst :type :callbackUrl :callbackMethod])
(def allowed-play-options [:loop])
(def allowed-record-options [:action :method :fileFormat :redirect :timeout :maxLength :playBeep :finishOnKey :recordSession :startOnDialAnswer :transcriptionType :transcriptionUrl :transcriptionMethod :callbackUrl :callbackMethod])
(def allowed-redirect-options [:method])
(def allowed-speak-options [:voice :languauge :loop])
(def allowed-wait-options [:length :silence :minSilence :beep])

(defn to-map
  "https://www.plivo.com/docs/xml/speak/"
  ([type allowed] (to-map type allowed {} []))
  ([type allowed options] (to-map type allowed options []))
  ([type allowed options nodes]
      (merge {:node type :children nodes} (select-keys options allowed))))

(defn conference [& args] (apply to-map "Conference" allowed-conference-options args))
(defn dial [& args] (apply to-map "Dial" allowed-dial-options args))
(defn number [& args] (apply to-map "Number" allowed-number-options args))
(defn user [& args] (apply to-map "User" allowed-user-options args))
(defn dtmf [& args] (apply to-map "DTMF" allowed-dtmf-options args))
(defn get-digits [& args] (apply to-map "GetDigits" allowed-get-digits-options args))
(defn hangup [& args] (apply to-map "Hangup" allowed-hangup-options args))
(defn message [& args] (apply to-map "Message" allowed-message-options args))
(defn play [& args] (apply to-map "Play" allowed-play-options args))
(defn record [& args] (apply to-map "Record" allowed-record-options args))
(defn redirect [& args] (apply to-map "Redirect" allowed-redirect-options args))
(defn speak [& args] (apply to-map "Speak" allowed-speak-options args))
(defn wait [& args] (apply to-map "Wait" allowed-wait-options args))


(defn format-node
  "Formats map and child to an XML node"
  [xml-node]
  (if (instance? String xml-node)
    xml-node
      (let [n-name (:node xml-node)
          n-children (reduce
            (fn [x-nodes x-node] 
              (conj x-nodes (format-node x-node)))
            [] (:children xml-node))
          n-options (dissoc xml-node :node :children)]
        (xml/element n-name n-options n-children))))


(defn respond
  "Formats a response vector as Plivo-supported XML"
  [& responses]
  (xml/emit-str
    (xml/element "Response" {} 
      (reduce (fn [x-nodes x-node]
        (conj x-nodes (format-node x-node))) [] responses))))