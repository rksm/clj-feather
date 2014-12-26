(in-ns 'user)

; -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
; add local cljs dirs to classpath
; -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

(def cljs-dir (get (System/getenv) "CLOJURESCRIPT_HOME"))

(def cp-additions-for-cljs
  (concat
    (map (partial str cljs-dir) ["/src/clj" "/src/cljs" "/test/cljs"])
    (->> (str cljs-dir "/lib/")
     clojure.java.io/file
     .listFiles
     (map str))))

(require '[cemerick.pomegranate])
(dorun (for [f cp-additions-for-cljs] (cemerick.pomegranate/add-classpath f)))

; -=-=-=-=-=-=-=-=-=-=-=-=-
; create browser repl env
; -=-=-=-=-=-=-=-=-=-=-=-=-
(require '[cljs.repl])
(require '[cljs.repl.browser])
(def brepl-env (cljs.repl.browser/repl-env))
; (cljs.repl/repl brepl-env)

(println "cljs browser repl env setup: user/brepl-env")
