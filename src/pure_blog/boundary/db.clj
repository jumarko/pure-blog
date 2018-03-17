(ns pure-blog.boundary.db
  (:require [clojure.java.jdbc :as sql])
  (:import duct.database.sql.Boundary))

(defprotocol UsersDb
  (get-user [db user-id]))

(defprotocol PostsDb
  (list-posts [db])
  (get-post [db post-id])
  (create-post [db]))

(extend-protocol UsersDb
  Boundary
  (get-user [{db :spec} user-id]
    (sql/query db "SELECT * FROM users where id = :user-id" {:user-id user-id})))

(extend-protocol PostsDb
  Boundary
  (list-posts [{db :spec}]
    (sql/query db "SELECT * FROM posts"))
  (get-post [{db :spec} post-id]
    (sql/query db "SELECT * FROM POSTS where id = :post-id" {:post-id post-id})))
