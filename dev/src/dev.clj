(ns dev
  (:refer-clojure :exclude [test])
  (:require [clojure.repl :refer :all]
            [fipp.edn :refer [pprint]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [clojure.java.jdbc :as sql]
            [duct.core :as duct]
            [duct.core.repl :as duct-repl]
            [eftest.runner :as eftest]
            [integrant.core :as ig]
            [integrant.repl :refer [clear halt go init prep reset]]
            [integrant.repl.state :refer [config system]]))

(duct/load-hierarchy)

(defn read-config []
  (duct/read-config (io/resource "dev.edn")))

(defn test []
  (eftest/run-tests (eftest/find-tests "test")))

(clojure.tools.namespace.repl/set-refresh-dirs "dev/src" "src" "test")

(when (io/resource "local.clj")
  (load "local"))

(integrant.repl/set-prep! (comp duct/prep-config read-config))

;; see James Reeves' suggestion: https://groups.google.com/forum/#!searchin/duct-clojure/repl|sort:date/duct-clojure/U5pUGC4eeCs/hGnjxztNBQAJ
(defn db
  "Helper function to access `db` component."
  []
  (val (ig/find-derived-1 system :duct.database/sql)))

(defn db-spec
  "db spec which can be directly used for clojure.java.jdbc queries."
  []
  (:spec (db)))
