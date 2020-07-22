(ns clojure-exercices-ch7.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(eval (read-string "(list \"Cazuza\" \"Star Wars\")"))

(defn infix
  [expr]
  (let [expr (loop [new-list () p-v nil [val & remaining-vals] expr]
               (if (nil? val)
                 new-list
                 (if (or (= (str val) "*") (= (str val) "/"))
                   (recur (concat (butlast new-list)
                                  (list (list val p-v (first remaining-vals))))
                          (list val p-v (first remaining-vals)) (rest remaining-vals))
                   (if (list? val)
                     (let [recursion-result (infix val)]
                       (recur (concat new-list (list recursion-result)) recursion-result remaining-vals))
                     (recur (concat new-list (list val)) val remaining-vals)))))]
    (loop [new-expr (first expr) [val & remaining-vals] expr]
      (if (nil? val)
        new-expr
        (recur (if (or (= (str val) "+") (= (str val) "-"))
                 (list val new-expr (first remaining-vals))
                 new-expr)
               remaining-vals)))))

(defmacro infix-macro
  [expr]
  (infix expr))

(infix-macro ((1 + 3) * 4 / 3))