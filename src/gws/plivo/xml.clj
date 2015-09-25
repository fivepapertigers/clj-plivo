(ns gws.plivo.xml
  (:require [clojure.data.xml :as xml]))

(defn speak
  "https://www.plivo.com/docs/xml/speak/"
  ([] (speak {}))
  ([options]
    (merge options {:node "Speak"})))

(defn format-node
  "Formats map and child to an XML node"
  [xml-node]

  (if (:children xml-node)
    (loop [node-children (:children xml-node) body ""]
      (let [[child & remaining] node-children]
        (if (empty? remaining)
          (format-node (dissoc (merge xml-node {:body body}) :children))
          (let [new-body (str body (format-node child))]
            (recur remaining new-body)))))    
      (let [n-name (:node xml-node) n-body (:body xml-node) n-options (dissoc xml-node :body :node)]
        (xml/element n-name n-options n-body))))


(defn respond
  "Formats a response map as Plivo-supported XML"
  [response]
  (xml/emit-str
    (xml/element "Response" {} 
      (reduce
        (fn [xml-nodes xml-node] (conj xml-nodes (format-node xml-node)))
        [] response))))