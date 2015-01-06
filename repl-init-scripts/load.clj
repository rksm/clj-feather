(ns user)

; classpath discovery

(def common-src-dirs ["src/main/clojure", "src/main/clj", "src/clojure", "src/clj", "src"])
(def common-test-dirs ["src/test/clojure", "src/test/clj", "src/test", "test/clojure", "test/clj", "test"])
(def class-dirs ["classes"])

(require '[clojure.java.io :as io])
(require '[cemerick.pomegranate])

(defn- first-existing-file
  [paths]
  (->> paths
     (map #(str (System/getProperty "user.dir") "/" %))
     (map io/file)
     (filter #(.exists %))
     first))

(some->> (first-existing-file common-src-dirs)
         (cemerick.pomegranate/add-classpath))

(some->> (first-existing-file common-test-dirs)
         (cemerick.pomegranate/add-classpath))

; -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
; dynamically loading packages
; much obliged: https://github.com/zcaudate/vinyasa/blob/master/src/vinyasa/pull.clj
(defn pull
  "Install and load packages at runtime via
  `cemerick.pomegranate/add-dependencies`.
  Use it as `(pull 'org.clojure/tools.analyzer.jvm)`"
  ([lib] (pull lib "RELEASE"))
  ([lib release]
     (cemerick.pomegranate/add-dependencies
           :coordinates [[lib release]]
           :repositories {"clojars" "http://clojars.org/repo"
                          "central" "http://repo1.maven.org/maven2/"})))

; -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
; script loading

(let [feather-dir (get (System/getenv) "CLOJURE_FEATHER")]
  (load-file (str feather-dir "/repl-init-scripts/ns-browser.clj"))
  (load-file (str feather-dir "/repl-init-scripts/java-reflection.clj")))
; (load-file "scripts/start-cljs-brepl.clj")

; -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
; startup notice
(require 'clojure.string)
(defn- nrepl-server-coords
  []
  (let [cmd (System/getProperty "sun.java.command")]
    {:port (some->> cmd (re-find #":port ([0-9]+)") second read-string)
     :host (some->> cmd (re-find #":bind ([^\s]+)") second read-string)}))

(let [server (nrepl-server-coords)]
  (println (format "nrepl server running on %s:%s"
                   (:host server) (:port server))))

