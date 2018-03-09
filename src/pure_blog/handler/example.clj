(ns pure-blog.handler.example
  (:require [ataraxy.core :as ataraxy]
            [ataraxy.response :as response] 
            [clojure.java.io :as io]
            [integrant.core :as ig]
            [selmer.parser :as selmer]))

(defn blog-posts []
  [#:post{:title "Why Functional?"
          :preview "This is a short essay on the benefits of Functional Programming."}])

(defn main-page
  []
  (selmer/render-file "pure_blog/handler/example/example.html"
                      {:posts (blog-posts)}))

(defmethod ig/init-key :pure-blog.handler/example [_ options]
  (fn [{[_] :ataraxy/result}]
    [::response/ok (main-page)]))
