(ns pure-blog.handler.root
  (:require [ataraxy.core :as ataraxy]
            [ataraxy.response :as response]
            [integrant.core :as ig]
            [pure-blog.boundary.db :as db]
            [selmer.parser :as selmer]))

(defn blog-posts []
  [#:post{:title "Why Functional?"
          :preview "This is a short essay on the benefits of Functional Programming."}])

(defn main-page
  [db-spec]
  (selmer/render-file "pure_blog/handler/root/main-page.html"
                      {:posts (blog-posts)
                       :tables (pr-str (db/get-tables db-spec))}))

(defmethod ig/init-key :pure-blog.handler/root [_ {:keys [db]}]
  (fn [{[_] :ataraxy/result}]
    [::response/ok (main-page db)]))
