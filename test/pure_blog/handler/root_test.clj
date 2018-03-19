(ns pure-blog.handler.root-test
  (:require [clojure.string :as string]
            [clojure.test :refer :all]
            [integrant.core :as ig]
            [pure-blog.boundary.db :as db]
            [ring.mock.request :as mock]))

(defrecord DummyDb []
  db/UsersDb
  (get-user [db user-id]
    {:email "jumarko@gmail.com"
     :first_name "Juraj"
     :last_name "Martinka"})
  db/PostsDb
  (list-posts [db]
    [{:title "Hello, Post"
      :text "Just starting my blog."}
     {:title "Second Post"
      :text "This is my second post."}]))

(deftest smoke-test
  (testing "example page exists"
    (let [handler  (ig/init-key :pure-blog.handler.root/main {:db (->DummyDb)})
          [status body] (handler (mock/request :get "/example"))]
      (is (= :ataraxy.response/ok status) "response ok")
      (is (string/includes? body "Hello, Post"))
      (is (string/includes? body "Second Post")))))
