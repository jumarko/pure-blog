(ns pure-blog.domain.posts
  (:require [pure-blog.boundary.db :as db]
            [pure-blog.domain.users :as users]))

(def max-post-preview-length 160)

(defn- post->readable
  "Converts db post data to the representation understood by the rest of the app."
  [db {:keys [id user_id title text created_date updated_date]}]
  (let [user (users/get-user db user_id)]
    (merge
     #:post{:id id
            :title title
            :preview (subs text 0 (min max-post-preview-length
                                       (count text)))
            :text text
            ;; TODO convert dates to proper format
            :created-date created_date
            :updated-date updated_date}
     user)))

(defn list-posts
  "Lists all existing posts in database.
  Only `:preview` is available for each post, not the full `:text.`"
  [db]
  (let [posts (db/list-posts db)]
    (mapv #(dissoc (post->readable db %) :text)
          posts)))

(defn get-post
  [db post-id]
  (post->readable db (db/get-post db)))
