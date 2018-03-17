(ns pure-blog.util)

(defn namespaced-map
  "Converts given map to the namespaced variant using `ns` namespace."
  [ns m]
  (into {} (for [[k v] m] [(keyword (name ns) (name k)) v])))
