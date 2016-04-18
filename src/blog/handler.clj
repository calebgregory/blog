(ns blog.handler
  (:require [clojure.java.io :as io]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [cheshire.core :refer :all]
            [blog.ctrl.posts :as posts-ctrl]
            [blog.utils :as util]))

(defroutes app-routes
  (GET "/" [] (posts-ctrl/index))
  (POST "/upload"
        {{{tempfile :tempfile filename :filename} :file} :params :as params}
        (io/copy tempfile (io/file "resources" "public" filename))
        "Success")
  (GET "/post/:y/:m/:d" [y m d]
       (let [[year month day] (map util/parse-int [y m d])]
         (posts-ctrl/post year month day)))
  (route/not-found "Not Found"))


(defn logging [app]
  (fn [req]
    (println "(>'')> " (select-keys req [:request-method :uri :params]))
    (app req)))

(def app
  (-> app-routes
      logging
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))
