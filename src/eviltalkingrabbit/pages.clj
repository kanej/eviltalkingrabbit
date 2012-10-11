(ns eviltalkingrabbit.pages
  (:use hiccup.core
        hiccup.page
        hiccup.bootstrap.page
        hiccup.form))

(defn main-page []
  (html5
    [:head
      [:title "Evil Talking Rabbit"]
      (include-js "//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js")
      (include-bootstrap)]
    [:body
      [:div.container
        [:div.row {:style "margin-top: 40px"}
          [:h1 {:style "text-align: center"} "ETR"]]
        [:div.row
          [:h3.muted {:style "text-align: center"} "Evil Talking Rabbit"]]
        [:div.row.offset3.span8 {:style "margin-top: 40px"}
          (form-to [:post "/speak"]
            [:div.input-append
              (text-field {:class "span5"} "Boom")
              [:button.btn {:type "submit"} "Speak"]])]]]))
