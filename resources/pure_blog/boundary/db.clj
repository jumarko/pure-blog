(ns pure-blog.boundary.db
  (:require [clojure.java.jdbc :as jdbc])
  (:import duct.database.sql.Boundary))

(defprotocol Db
  (get-tables [db]))

(extend-protocol Db
  Boundary
  (get-tables [{:keys [spec]}]
    ;; https://stackoverflow.com/questions/14730228/postgres-query-to-list-all-table-names
    (jdbc/query spec ["SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE';"])))
