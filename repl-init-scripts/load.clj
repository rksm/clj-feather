(let [feather-dir (get (System/getenv) "CLOJURE_FEATHER")]
  (load-file (str feather-dir "/repl-init-scripts/dynamic-deps.clj"))
  (load-file (str feather-dir "/repl-init-scripts/ns-browser.clj"))
  (load-file (str feather-dir "/repl-init-scripts/java-reflection.clj")))
; (load-file "scripts/start-cljs-brepl.clj")
