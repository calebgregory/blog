(ns blog.ctrl.posts
  (:require [blog.models.post :as m]))

(defn index [] "Hello - index")

(defn posts []
  (let [posts (m/get-posts)]
    (map :body posts)))

(defn post
  ([id]
   (let [post (m/get-post id)]
     (:body post)))
  ([y m d]
   (let [posts (m/get-post y m d)]
     (map :body posts))))
