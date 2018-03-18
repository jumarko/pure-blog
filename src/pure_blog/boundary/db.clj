(ns pure-blog.boundary.db
  (:require [clojure.java.jdbc :as sql])
  (:import duct.database.sql.Boundary))

(defprotocol UsersDb
  (get-user [db user-id])
  (create-user [db user-data]))

(defprotocol PostsDb
  (list-posts [db])
  (get-post [db post-id])
  (create-post [db post-data])
  (update-post [db post-id post-data]))

(extend-protocol UsersDb
  Boundary
  (get-user [{db :spec} user-id]
    (first (sql/query db ["SELECT * FROM users where id = ?" user-id])))
  (create-user [{db :spec} user-data]
    (sql/insert! db :users user-data)))

(extend-protocol PostsDb
  Boundary
  (list-posts [{db :spec}]
    (sql/query db "SELECT * FROM posts"))
  (get-post [{db :spec} post-id]
    (first (sql/query db ["SELECT * FROM posts where id = ?" post-id])))
  (create-post [{db :spec} post-data]
    (sql/insert! db :posts post-data))
  (update-post [{db :spec} post-id post-data]
    (sql/update! db :posts post-data ["id = ?" post-id])))
