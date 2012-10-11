(ns eviltalkingrabbit.pages
  (:use hiccup.core
        hiccup.page
        hiccup.bootstrap.page
        hiccup.form))

(def alert-close-button
  [:button {:type "button" :class "close" :data-dismiss "alert"} "x"])

(defn alert-bar [alert-type]
  (cond (= alert-type :none) nil
        (= alert-type :success) [:div.alert.alert-success alert-close-button "The rabbit agrees to speak for you"]
        (= alert-type :failure) [:div.alert.alert-error alert-close-button "The rabbit refused for mysterious internet reasons!"]))

(defn main-page 
  ([] (main-page :none))
  ([alert-type]
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
            [:h3.muted {:style "text-align: center; margin-top: 10px"} "Evil Talking Rabbit"]]
          [:div.row.offset3.span5 {:style "min-height: 60px; margin-top: 10px"}
            (alert-bar alert-type)]
          [:div.row.offset3.span8 
            (form-to [:post "/speak"]
              (text-field {:class "span5"} "phrase"))]]])))
