(ns pure-blog.util-test
  (:require [pure-blog.util :as u]
            [clojure.test :refer :all]))

(deftest namespaced-map
  (let [unqualified-keys {:name "Juraj" :age 32}
        qualified-keys #:user{:name "Juraj" :age 32}]
    (testing "accepts keyword"
      (is (= qualified-keys (u/namespaced-map :user unqualified-keys))))
    (testing "accepts string"
      (is (= qualified-keys (u/namespaced-map "user" unqualified-keys))))
    (testing "empty map - it doesn't really matter whather it's qualified or not"
      (is (= #:user{} (u/namespaced-map :user {})))
      (is (= {} (u/namespaced-map :user {}))))
    (testing "accepts nil map"
      (is (= {} (u/namespaced-map :user nil))))))

