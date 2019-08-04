(ns user
    (:use [figwheel-sidecar.repl-api :as ra]))
;; This namespace is loaded automatically by nREPL

;; read project.clj to get build configs

(def project-config (->> "project.clj"
                         slurp
                         read-string
                         (drop-while #(not= % (or :figwheel :profiles)))
                         (apply hash-map)))

(def cljs-builds (get-in project-config [:profiles :dev :cljsbuild :builds]))
(def figwheel-options (get project-config :figwheel {}))

(defn start-figwheel
      "Start figwheel for one or more builds"
      [& build-ids]
      (ra/start-figwheel!
        {:build-ids  build-ids
         :all-builds cljs-builds
         :figwheel-options figwheel-options
         })
      (ra/cljs-repl))

(defn stop-figwheel
      "Stops figwheel"
      []
      (ra/stop-figwheel!))
