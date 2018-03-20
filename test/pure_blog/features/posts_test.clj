(ns pure-blog.features.posts-test
  (:require [pure-blog.features.posts :as p]
            [clojure.test :refer [deftest testing is are]]))


(deftest test-preview
  (testing "return the first non-empty-line"
    (is (= "first line"
           (p/preview  "   \n\n  \nfirst line\nsecond line\n   "))))
  (testing "return nil for nil"
    (is (nil? (p/preview nil))))
  (testing "return nil for empty string"
    (is (nil? (p/preview ""))))
  (testing "return nil for only blank lines"
    (is (nil? (p/preview "\n  \n")))))
