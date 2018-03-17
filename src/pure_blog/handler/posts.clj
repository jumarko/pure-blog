(ns pure-blog.handler.posts
  (:require [ataraxy.response :as response]
            [integrant.core :as ig]
            [pure-blog.domain.posts :as posts]
            [pure-blog.util :as u]
            [selmer.parser :as selmer]))

;; TODO: replace hardcoded user id with a real user
(def user-id 1)

(defn- create-post
  [db post]
  (posts/create-post db user-id (u/namespaced-map "post" post)))

(defn- post-detail-page [post]
  (selmer/render-file "pure_blog/handler/posts/post.html"
                      post))

(defmethod ig/init-key ::create [_ {:keys [db]}]
  (fn [{[_ post] :ataraxy/result}]
    (let [_ (create-post db post)]
      [::response/see-other "/"])))

(defmethod ig/init-key ::get [_ {:keys [db]}]
  (fn [{[_ id] :ataraxy/result}]
    (if-let [post (posts/get-post db id)]
      [::response/ok (post-detail-page post)]
      [::response/not-found {:error :not-found}])))
