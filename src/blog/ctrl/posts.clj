(ns blog.ctrl.posts
  (:require [cheshire.core :refer :all]
            [clj-time.core :as t]
            [markdown.core :as md]
            [blog.models.post :as m]))

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

(defn create
  [tempfile]
  (let [[y m d] ((juxt t/year t/month t/day) (t/now))
        md-content (slurp tempfile)
        html (md/md-to-html-string md-content)]
    (m/create-post y m d html)))
