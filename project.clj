(defproject pure-blog "0.1.0-SNAPSHOT"
  :description "My own blog app implementation for the purpose of PurelyFunctional.tv exercise."
  :url "https://github.com/jumarko/pure-blog"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [duct/core "0.7.0"]
                 [duct/module.logging "0.4.0"]
                 [duct/module.web "0.7.0"]
                 [duct/module.ataraxy "0.3.0"]
                 [duct/module.sql "0.5.0"]
                 [org.postgresql/postgresql "42.2.5"]
                 [selmer "1.12.12"]
                 [buddy/buddy-hashers "1.3.0"]
                 [org.clojure/test.check "0.10.0-alpha4"]
                 [igviz "0.1.0"]]
  :plugins [[duct/lein-duct "0.12.0"]]
  :main ^:skip-aot pure-blog.main
  :resource-paths ["resources" "target/resources"]
  :prep-tasks     ["javac" "compile" ["run" ":duct/compiler"]]
  :profiles
  {:dev  [:project/dev :profiles/dev]
   :repl {:prep-tasks   ^:replace ["javac" "compile"]
          :repl-options {:init-ns user}}
   :uberjar {:aot :all}
   :profiles/dev {}
   :project/dev  {:source-paths   ["dev/src"]
                  :resource-paths ["dev/resources"]
                  :dependencies   [[integrant/repl "0.3.1"]
                                   [eftest "0.5.7"]
                                   [kerodon "0.9.0"]]}})
