(ns user)

; classpath discovery

(def common-src-dirs ["src/main/clojure", "src/main/clj", "src/clojure", "src/clj", "src", "classes"])
(def common-test-dirs ["src/test/clojure", "src/test/clj", "src/test", "test/clojure", "test/clj", "test"])

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
