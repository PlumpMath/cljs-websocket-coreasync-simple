(ns cljs-websocket-coreasync-simple.core
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [cljs.core.async :refer [chan]]
            [cljs.reader :refer [read-string]]
            [cljs.core.async :refer [<! >! chan]]))

(def coms (atom {:ws-url ""
                 :websocket nil
                 :ws-only-send-chan nil
                 :app-send-chan nil
                 :app-recv-chan nil}))

(defn- make-websocket! []
  "Make new websocket, wire up send/recv channels."
  (swap! coms assoc-in [:websocket] (js/WebSocket. (:ws-url @coms)))
  (swap! coms assoc-in [:ws-only-send-chan] (chan))
  (aset (:websocket @coms)
        "onopen"
        #(go-loop []
           (let [msg (<! (:ws-only-send-chan @coms))]
             (.send (:websocket @coms) msg)
             (recur))))
  (aset (:websocket @coms)
        "onmessage"
        #(go
           (let [resp (aget % "data")]
             (>! (:app-recv-chan @coms) resp)))))

(defn- ensure-websocket-connected! []
  "If websocket is not in ready state, discard and make new one."
  (if (not (= (.-readyState (:websocket @coms)) 1))
    (make-websocket!)))

(defn- monitor-send-msg-queue
  [app-send-chan]
  (go-loop []
    (ensure-websocket-connected!)
    (let [msg (<! app-send-chan)
          ws (:websocket @coms)
          ws-only-send-chan (:ws-only-send-chan @coms)]
      (>! ws-only-send-chan msg))
    (recur)))

(defn init-websocket!
  [app-send-chan app-recv-chan ws-url]
  (swap! coms assoc-in [:app-send-chan] app-send-chan)
  (swap! coms assoc-in [:app-recv-chan] app-recv-chan)
  (swap! coms assoc-in [:ws-url] ws-url)
  (make-websocket!)
  (monitor-send-msg-queue app-send-chan))


