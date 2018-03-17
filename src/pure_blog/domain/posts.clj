(ns pure-blog.domain.posts
  (:require [pure-blog.boundary.db :as db]
            [pure-blog.domain.users :as users])
  (:import java.util.Date))

(def max-post-preview-length 160)

(defn- db-post->readable
  "Converts db post data to the representation understood by the rest of the app."
  [db {:keys [id user_id title text created_date updated_date]}]
  (let [user (users/get-user db user_id)]
    (merge
     #:post{:id id
            :title title
            :preview (some-> text (subs 0 (min max-post-preview-length
                                        (count text))))
            :text text
            ;; TODO convert dates to proper format
            :created-date created_date
            :updated-date updated_date}
     user)))

(defn- readable->db-post
  [user-id {:post/keys [id title text created-date updated-date]}]
  {:id id
   :user_id user-id
   :title title
   :text text
   :created_date created-date
   :updated_date updated-date})

(defn list-posts
  "Lists all existing posts in database.
  Only `:preview` is available for each post, not the full `:text.`"
  [db]
  (let [posts (db/list-posts db)]
    (mapv #(dissoc (db-post->readable db %) :text)
          posts)))

(defn get-post
  [db post-id]
  (when-let [db-post (db/get-post db post-id)]
    (db-post->readable db db-post)))

(defn create-post
  "Creates new post using given data.
  The post's created date is set to the current date/time."
  [db {user-id :user/id} post-data]
  (db/create-post
   db
   (-> 
    (readable->db-post user-id (assoc post-data :created-date (Date.)))
    (dissoc :id))))


(comment
  (create-post (dev/db) #:user{:id 1} #:post{:id 0 :title "awesome"})
  (list-posts (dev/db))
  (get-post (dev/db) 0)
  )
