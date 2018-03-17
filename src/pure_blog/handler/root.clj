(ns pure-blog.handler.root
  (:require [ataraxy.response :as response]
            [integrant.core :as ig]
            [pure-blog.domain.posts :as posts]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
            [selmer.parser :as selmer]))

(defn main-page [posts]
  (selmer/render-file "pure_blog/handler/root/main-page.html"
                      {:posts posts
                       :csrf-token *anti-forgery-token*}))

(defmethod ig/init-key ::main [_ {:keys [db]}]
  (fn [{[_] :ataraxy/result}]
    [::response/ok (main-page (posts/list-posts db))]))
