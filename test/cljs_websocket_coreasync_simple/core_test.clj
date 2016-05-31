(ns cljs-websocket-coreasync-simple.core-test
  (:require [cljs.test :refer-macros [use-fixtures async deftest is testing run-tests]]
            [cljs-websocket-coreasync-simple.core :refer [init-websocket!]]))

(use-fixtures :once 
  {:before (fn [] (println "before"))
   :after  (fn [] (println "after"))})

(deftest init-websocket!-test
  (let [ws-url "ws://localhost:8061"
        send-chan (chan)
        recv-chan (chan)]
    (init-websocket! ws-url send-chan recv-chan)))

(run-tests)
