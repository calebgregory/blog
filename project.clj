(defproject blog "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.0"]
                 [environ "1.0.2"]
                 [com.novemberain/monger "3.0.2"]
                 [ring/ring-defaults "0.1.5"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler blog.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}
   :default [:base :system :user :provided :dev :local-dev]}
  :aliases {"test" ["with-profile" "+local-test" "test"]})
