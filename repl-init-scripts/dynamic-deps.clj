(in-ns 'user)
(require '[cemerick.pomegranate])

(println "Running dynamic-deps.clj")

; much obliged: https://github.com/zcaudate/vinyasa/blob/master/src/vinyasa/pull.clj
(defn pull
  ([lib] (pull lib "RELEASE"))
  ([lib release]
     (cemerick.pomegranate/add-dependencies
           :coordinates [[lib release]]
           :repositories {"clojars" "http://clojars.org/repo"
                          "central" "http://repo1.maven.org/maven2/"})))
