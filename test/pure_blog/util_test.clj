(ns pure-blog.util-test
  (:require [clojure.test :refer :all]
            [clojure.test.check.clojure-test :as ctest]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [pure-blog.util :as u]))

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

(def name-gen (gen/not-empty gen/string-alphanumeric))
(def age-gen (gen/such-that (complement zero?) gen/pos-int))
(def map-gen (gen/hash-map :name name-gen :age age-gen))

(ctest/defspec namespaced-map-generative 50
  (prop/for-all [my-map map-gen]
                (= "user" (-> (u/namespaced-map :user my-map) ffirst namespace))))
