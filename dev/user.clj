(ns user
  (:require [figwheel-sidecar.repl-api :as ra]))

(defn start []
  (ra/start-figwheel!)
  (ra/cljs-repl "dev"))

(defn stop [] (ra/stop-figwheel!))
;; (start)

(defn start-fw []
  (ra/start-figwheel!
   {:figwheel-options
    {}
    :build-ids ["dev"]
    ;; :websocket-url "ws://localhost:3449/figwheel-ws2" ; doesn't seem to affect anything
    :all-builds
    [{:id "dev"
      :figwheel true
      :source-paths ["src"]
      :compiler {:main 'cljs-websocket-coreasync-simple.core
                 :asset-path "js"
                 :output-to "resources/public/js/main.js"
                 :output-dir "resources/public/js"
                 :verbose true}}]})
  (ra/cljs-repl))
