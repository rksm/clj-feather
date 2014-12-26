(in-ns 'user)
(require '[clojure.data.json])
(require '[clojure.repl])

(defn source-for-symbol [sym]
  (try
    (with-out-str (eval `(clojure.repl/source ~sym)))
    (catch Exception e (str "Error retrieving source: " e))))

(defn intern-info
  [intern]
    (let [data (meta (val intern))]
      (if-let [ns (:ns data)]
        (let [name (:name data)
              ns-name (.name ns)
              tag (if-let [tag (:tag data)] (str tag))
              source (source-for-symbol (symbol (format "%s/%s" ns-name name)))]
            (merge data {:ns ns-name :tag tag :source source})))))

(defn stringify [obj]
  (cond
    (var? obj) (:name (meta obj))
    (or (string? obj) (symbol? obj)(keyword? obj)) (name obj)
    :else (str obj)))

(defn namespace-info-json [ns]
  (let [symbol-data (map intern-info (ns-interns ns))]
    (clojure.data.json/write-str symbol-data
      :key-fn stringify
      :value-fn (fn [_ val] (stringify val)))))
