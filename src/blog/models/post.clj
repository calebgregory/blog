(ns blog.models.post
  (:require [environ.core :refer [env]]
            [monger.collection :as mc]
            [monger.core :as mg])
  (:import [com.mongodb MongoOptions ServerAddress]
           org.bson.types.ObjectId))

(defn grab-db []
  (let [^MongoOptions opts (mg/mongo-options {:threads-allowed-to-block-for-connection-multiplier 300})
        ^ServerAddress sa (mg/server-address "127.0.0.1" 27017)
        conn (mg/connect sa opts)
        db (mg/get-db conn "blog-dev")]
    db))

(defn get-posts []
  (let [db (grab-db)]
    (mc/find-maps db "posts")))

(defn get-post
  ([oid-string]
   (let [db (grab-db)
         oid (ObjectId. oid-string)]
     (mc/find-one-as-map db "posts" {:_id oid})))
  ([y m d]
   (let [db (grab-db)]
     (mc/find-maps db "posts" {:year y :month m :day d}))))

(defn insert-post [y m d body]
  (let [db (grab-db)]
    (mc/insert db "posts" {:_id (ObjectId.) :year y :month m :day d :body body})))

(defn delete-post [oid-string]
  (let [db (grab-db)
        oid (ObjectId. oid-string)]
    (mc/remove-by-id db "posts" oid)))
