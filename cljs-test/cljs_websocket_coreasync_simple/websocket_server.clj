(ns cljs-websocket-coreasync-simple.websocket-server
  (:require [org.httpkit.server
             :refer [with-channel on-close websocket? on-receive send! run-server]]))

(defn websocket-handler [req]
  (with-channel req channel
    (on-receive channel (fn [data]
                          (println data)
                          (send! channel data)))))

(defn runserver [] (run-server websocket-handler {:port 8062}))
