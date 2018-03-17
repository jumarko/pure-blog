(ns pure-blog.handler.root
  (:require [ataraxy.core :as ataraxy]
            [ataraxy.response :as response]
            [integrant.core :as ig]
            [pure-blog.domain.posts :as posts]
            [selmer.parser :as selmer]))


(defn main-page
  [db]
  (selmer/render-file "pure_blog/handler/root/main-page.html"
                      {:posts (posts/list-posts db)}))

(defmethod ig/init-key :pure-blog.handler/root [_ {:keys [db]}]
  (fn [{[_] :ataraxy/result}]
    [::response/ok (main-page db)]))
