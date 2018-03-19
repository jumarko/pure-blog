(ns pure-blog.features.users
  (:require [buddy.hashers :as hashers]
            [pure-blog.boundary.db :as db]))

(defn get-user [db user-id]
  (when-let [{:keys [id first_name last_name email admin last_login is_active]}
               (db/get-user db user-id)]
    #:user{:id id
           :first-name first_name
           :last-name last_name
           :email email
           :admin? admin
           :last-login-date last_login
           :active? is_active}))

(defn create-user
  "Creates new user using given data.
  Password is hashed before and never stored in plain text."
  [db {:user/keys [id first-name last-name email admin? password]}]
  (db/create-user
   db
   {:id id
    :first_name first-name
    :last_name last-name
    :email email
    :pass (hashers/encrypt password)
    :admin (true? admin?)
    :is_active true}))

(comment

  (create-user (dev/db) #:user{:id 1
                               :first-name "Juraj"
                               :last-name "Martinka"
                               :email "jumarko@gmail.com"
                               :admin? true
                               :password "XXX"} )

  (get-user (dev/db) 1)

  )
