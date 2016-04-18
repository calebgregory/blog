(ns blog.ctrl.posts
  (:require [blog.models.post :as m]
            [cheshire.core :refer :all]))

(defn index [] "Hello - index")

(defn posts []
  (let [posts (m/get-posts)]
    (map :body posts)))

(defn post
  ([id]
   (let [post (m/get-post id)]
     (:body post)))
  ([y m d]
   (let [posts (m/get-post y m d)
         post-bodies (map :body posts)]
     (generate-string post-bodies))))
