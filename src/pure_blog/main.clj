(ns pure-blog.main
  (:gen-class)
  (:require [clojure.java.io :as io]
            [duct.core :as duct]))

(duct/load-hierarchy)

(defn -main [& args]
  (let [keys (or (duct/parse-keys args) [:duct/daemon])]
    (-> (duct/resource "pure_blog/config.edn")
        (duct/read-config keys)
        (duct/exec-config keys))))
