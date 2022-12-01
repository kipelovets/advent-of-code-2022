(ns leiningen.new.advent-day
  (:require [leiningen.new.templates :as tmpl]
            [leiningen.core.main :as main]))

(def render (tmpl/renderer "advent_day"))

(defn advent-day
  "FIXME: write documentation"
  [name]
  (let [data {:name name
              :sanitized (tmpl/name-to-path name)}]
    (main/info "Generating fresh 'lein new' advent-day project.")
    (tmpl/->files data
                  ["src/{{sanitized}}/core.clj" (render "core.clj" data)]
                  ["test/{{sanitized}}/core_test.clj" (render "core_test.clj" data)]
                  ["input" (render "input" data)]
                  ["project.clj" (render "project.clj" data)]
                  ["tests.edn" (render "tests.edn" data)])))
