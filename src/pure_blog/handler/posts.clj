(ns pure-blog.handler.posts
  (:require [ataraxy.response :as response]
            [integrant.core :as ig]
            [pure-blog.domain.posts :as posts]
            [pure-blog.util :as u]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
            [selmer.parser :as selmer]))

;; TODO: replace hardcoded user id with a real user
(def user-id 1)

(defn- create-post
  [db post]
  (posts/create-post db user-id (u/namespaced-map "post" post)))

(defn- update-post
  [db post-id post]
  (posts/update-post db post-id (u/namespaced-map "post" post)))

(defn- post-edit-page [post]
  (selmer/render-file "pure_blog/handler/posts/post-edit.html"
                      (assoc post
                             :csrf-token *anti-forgery-token*)))

(defn- post-detail-page [{:post/keys [id] :as post}]
  (selmer/render-file "pure_blog/handler/posts/post.html"
                      (assoc post :post-edit-link (str "/posts/" id "/edit"))))

(defmethod ig/init-key ::update [_ {:keys [db]}]
  (fn [{[_ id post] :ataraxy/result}]
    (let [_ (update-post db id post)]
      [::response/see-other (str "/posts/" id)])))

(defmethod ig/init-key ::edit [_ {:keys [db]}]
  (fn [{[_ id] :ataraxy/result}]
    (if-let [post (posts/get-post db id)]
      [::response/ok (post-edit-page post)]
      [::response/not-found {:error :not-found}])))

(defmethod ig/init-key ::create [_ {:keys [db]}]
  (fn [{[_ post] :ataraxy/result}]
    (let [_ (create-post db post)]
      [::response/see-other "/"])))

(defmethod ig/init-key ::get [_ {:keys [db]}]
  (fn [{[_ id] :ataraxy/result}]
    (if-let [post (posts/get-post db id)]
      [::response/ok (post-detail-page post)]
      [::response/not-found {:error :not-found}])))
