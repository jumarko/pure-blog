(ns pure-blog.handler.posts-test
  (:require [clojure.test :refer :all]
            [integrant.core :as ig]
            [pure-blog.boundary.db :as db]
            [pure-blog.handler.posts :as p]
            [ring.mock.request :as mock]
            [clojure.string :as string])
  )

;; let's add the remaining methods required by posts functionality
(defn init-db []
  (atom [{:title "Hello, Post"
          :text "Just starting my blog."}
         {:title "Second Post"
          :text "This is my second post."}]))

(defrecord DummyDb [post-db]
  db/UsersDb
  (get-user [db user-id]
    {:email "jumarko@gmail.com"
     :first_name "Juraj"
     :last_name "Martinka"})
  db/PostsDb
  (list-posts [db] @post-db)
  (get-post [db post-id] (@post-db (dec post-id)))
  (create-post [db post-data]
    (swap! post-db conj post-data))
  (update-post [db post-id post-data]
    (swap! post-db assoc (dec post-id) post-data)))

(defn- test-handler-with-db [test-name handler-key request check-fn]
  (testing test-name
    (let [posts (init-db)
          handler (ig/init-key handler-key {:db (->DummyDb posts)})
          old-db @posts
          [status body] (handler request)
          new-db @posts]
      (check-fn old-db new-db status body))))

;; TODO: it'd be good to check that routing works as expected but with mocks we're not able to do that
(deftest test-posts
  (let [post-title "New post!"
        post-text "Random text in the body of the post."]
    (test-handler-with-db "create a new post"
                          :pure-blog.handler.posts/create
                          (assoc (mock/request :post "/posts")
                                 :ataraxy/result [:dummy {:title post-title
                                                          :text post-text}])
                          (fn [old-db new-db status body]
                            (is (= :ataraxy.response/see-other status))
                            (is (= (inc (count old-db)) (count new-db)))))
    (test-handler-with-db "get an existing post"
                          :pure-blog.handler.posts/get
                          (assoc (mock/request :get "/posts/2")
                                 :ataraxy/result [:dummy 2])
                          (fn [old-db new-db status body]
                            (is (= :ataraxy.response/ok status))
                            (is (string/includes? body "This is my second post."))))))
