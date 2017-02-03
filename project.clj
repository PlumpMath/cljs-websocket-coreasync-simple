(defproject websocket-client "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://github.com/ftravers/websocket-client"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha14"]
                 [org.clojure/clojurescript "1.9.456"]
                 [org.clojure/core.async "0.2.395" :exclusions [org.clojure/tools.reader]]
                 [crypto-random "1.2.0"]
                 [com.taoensso/timbre "4.8.0"]]

  :source-paths ["src/cljs" "dev"]

  :plugins [[lein-figwheel "0.5.3-1"]
            [lein-cljsbuild "1.1.3" :exclusions [[org.clojure/clojure]]]
            [cider/cider-nrepl "0.15.0-SNAPSHOT"]
            [lein-doo "0.1.6"]]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]  

  :cljsbuild
  {:builds
   [{:id "dev"
     :source-paths ["src/cljs" "dev"]
     :figwheel true
     :compiler {:main websocket-client.core
                :asset-path "js/compiled/out"
                :output-to "resources/public/js/compiled/app.js"
                :output-dir "resources/public/js/compiled/out"
                :source-map-timestamp true}}
    {:id "test"
     :source-paths ["src/cljs" "cljs-test" "dev"]
     :compiler {:optimizations :none
                :output-to "out/testable.js"
                :main websocket-client.runner}}]}
  
  :profiles {:dev {:dependencies [[figwheel-sidecar "0.5.3-1"]
                                  [com.cemerick/piggieback "0.2.1"]
                                  [http-kit "2.1.19"]]
                   :source-paths ["src/cljs" "src/clj" "dev"]
                   ;; for CIDER
                   ;; :plugins [[cider/cider-nrepl "0.12.0"]]
                   :repl-options { ; for nREPL dev you really need to limit output
                                  :init (set! *print-length* 50)
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}
             :test {:dependencies [[figwheel-sidecar "0.5.3-1"]
                                   [com.cemerick/piggieback "0.2.1"]
                                   [http-kit "2.1.19"]
                                   ]
                    :source-paths ["src/cljs" "dev"]
                    ;; for CIDER
                    ;; :plugins [[cider/cider-nrepl "0.12.0"]]
                    :repl-options { ; for nREPL dev you really need to limit output
                                   :init (set! *print-length* 50)
                                   :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}
  )
