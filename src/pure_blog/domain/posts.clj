(ns pure-blog.domain.posts
  (:require [pure-blog.boundary.db :as db]
            [pure-blog.domain.users :as users]))

(def max-post-preview-length 160)

(defn list-posts [db]
  (let [posts (db/list-posts db)]
    (mapv
     (fn post->readable [{:keys [id user_id title text created_date updated_date]}]
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
     posts)))
