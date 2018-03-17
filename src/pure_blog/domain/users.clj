(ns pure-blog.domain.users
  (:require [pure-blog.boundary.db :as db]))

(defn get-user [db user-id]
  (let [{:keys [id first_name last_name email admin last_login is_active]}
        (db/get-user db user-id)]
    #:user{:id id
           :first-name first_name
           :last-name last_name
           :email email
           :admin? admin
           :last-login-date last_login
           :active? is_active}))
