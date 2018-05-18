(ns hello-processing.core
  (:require [quil.core :as q]
            [quil.middleware :as m])
  (:import [controlP5 ControlEvent ControlP5])
  (:gen-class))

(defn ensyuu-1 []
 (q/defsketch draw-square
   :title "Drawing Square"
   :setup (fn []
            (q/frame-rate 30)
            (q/color-mode :rgb)
            {:color 0})
   :size [200 200]
   :draw (fn [state]
           (q/background 255)
           (q/fill (:color state) 0 0)
           (q/rect 10 10 100 100))
   :middleware [m/fun-mode m/pause-on-error]))

;; (ensyuu-1)

(defn ensyuu-2-1 []
  (q/defsketch draw-squares
    :title "Draw Squares"
    :setup (fn []
             (q/frame-rate 30)
             (q/color-mode :rgb)
             {:black 0
              :white 255})
    :size [200 200]
    :draw (fn [state]
                    (loop [i 0]
                      (when (> 10 i)
                        (do (if (zero? (mod i 2))
                              (q/fill 0)
                              (q/fill 255))
                            (q/rect (* i 10) (* i 10) 10 10)
                            (recur (inc i))))))
    :middleware [m/fun-mode m/pause-on-error]))

;; (ensyuu-2-1)

(defn ensyuu-2-2 []
  (q/defsketch draw-ichimatsu
    :title "Draw Ichimatsu"
    :setup (fn []
             (q/frame-rate 30)
             (q/color-mode :rgb)
             {})
    :size [200 200]
    :draw (fn [state]
            (loop [i 0]
              (when (< i (* 10 10))
                (do
                  (if (zero? (mod (+ (quot i 10)
                                     (mod i 10)) 2))
                    (q/fill 255 0 0)
                    (q/fill 255 255 255))
                  (q/rect (* 20 (quot i 10))
                          (* 20 (mod i 10))
                          20
                          20)
                  (recur (inc i)))))
            )
    :middleware [m/fun-mode m/pause-on-error]))

;; (ensyuu-2-2)

(defn ensyuu-3 []
  (q/defsketch mouse-press-event
    :title "Mouse Event"
    :setup (fn []
             (q/frame-rate 30)
             (q/color-mode :rgb)
             {})
    :size [200 200]
    :mouse-pressed (fn [state event]
                     (q/stroke 255 0 0))
    :mouse-released (fn [state event]
                     (q/stroke 0 0 0))
    :draw (fn [state]
           (q/fill 255 255 255)
           (q/rect 20 20 60 60))
    :middleware [m/fun-mode m/pause-on-error]))

;; (ensyuu-3)

(defn ensyuu-4 []
  (let [func (atom 0)]
   (q/defsketch key-press-event
     :title "Key Press Event"
     :setup (fn []
              (q/frame-rate 30)
              (q/color-mode :rgb)
              (q/background 100)
              {})
     :size [300 300]
     :mouse-pressed (fn [state event]
                      (q/stroke 255 0 0))
     :mouse-released (fn [state event]
                       (q/stroke 0 0 0))
     :key-pressed (fn [_ {:keys [key key-code]}]
                    (case key
                      :r (q/fill 255 0 0)
                      :g (q/fill 0 255 0)
                      :b (q/fill 0 0 255)
                      :c (reset! func (if (zero? @func) 1 0))))
     :draw (fn [state]
             (let [r (if (q/mouse-pressed?) 20 10)]
               (if (zero? @func)
                 (q/ellipse (q/mouse-x) (q/mouse-y) r r)
                 (q/rect (q/mouse-x) (q/mouse-y) r r))))
     :middleware [m/fun-mode m/pause-on-error])))

;; (ensyuu-4)


(defn ensyuu-5 []
  (let [func (atom 0)]
    (q/defsketch sikaku
      :title "sikaku"
      :setup (fn []
               (q/frame-rate 30)
               (q/color-mode :rgb)
               (q/background 100)
               {:len 0})
      :size [300 300]
      :update (fn [state]
                {:len (if (== (:len state) 800) 0 (inc (:len state)))})
      :draw (fn [state]
              (let [len (:len state)]
                (cond
                  (and (< 0 len) (<= len 200))
                  (q/ellipse (+ 50 len) 50 10 10)
                  (and (< 200 len) (<= len 400))
                  (q/ellipse 250 (- len 150) 10 10)
                  (and (< 400 len) (<= len 600))
                  (q/ellipse (- 250 (- len 400)) 250 10 10)
                  (and (< 600 len) (<= len 800))
                  (q/ellipse 50 (- 250 (- len 600)) 10 10)
                  )
                ))
      :middleware [m/fun-mode m/pause-on-error])))

;; (ensyuu-5)

(defn ensyuu-6 []
  (q/defsketch input-date
    :title "Input Date"
    :size [400 150]
    :setup (fn []
             (let [cp (ControlP5. (quil.applet/current-applet))
                   dl1 (.addScrollableList cp "Month")
                   dl2 (.addScrollableList cp "Date")
                   dl3 (.addScrollableList cp "Week")
                   myfunc (fn [dl]
                            (-> dl
                                (.setSize 100 120)
                                (.setBarHeight 20)
                                (.setItemHeight 20)))
                   _ (-> dl1
                         (.setPosition 50 10)
                         (myfunc)
                         (.addItems
                          '("Jan." "Feb." "Mar." "Apr." "May" "Jun."
                            "Jul." "Aug." "Sep." "Oct." "Nov." "Dec.")))
                   _ (-> dl2
                         (.setPosition 150 10)
                         (myfunc)
                         (.addItems
                          (map str (take 31 (iterate inc 1)))))
                   _ (-> dl3
                         (.setPosition 250 10)
                         (myfunc)
                         (.addItems
                          '("Mon." "Tue." "Wed." "Thu."
                            "Fri." "Sat." "Sun.")))]
               (q/frame-rate 30)
               (q/color-mode :rgb)))
    :draw (fn [] (q/background 100))
    :middleware [m/pause-on-error]))

;; (ensyuu-6)

(defn -main []
  (ensyuu-1)
  (ensyuu-2-1)
  (ensyuu-2-2)
  (ensyuu-3)
  (ensyuu-4)
  (ensyuu-5)
  (ensyuu-6))

;; (defn add-button []
;;   (q/defsketch button
;;     :title "button"
;;     :size [200 100]
;;     :setup  (fn []
;;               (let [cp (ControlP5. (quil.applet/current-applet))
;;                     _ (-> cp
;;                           (.addButton "b1")
;;                           (.setSize 50 24)
;;                           (.setPosition 120 25))
;;                     _ (-> cp
;;                           (.addButton "b2")
;;                           (.setSize 50 24)
;;                           (.setPosition 120 50))]
;;                 (q/frame-rate 30)
;;                 (q/color-mode :rgb)
;;                 (q/background 255))
;;               {})
;;     :draw (fn []
;;             (q/fill 0 0 0)
;;             (q/text-size 25)
;;             (q/text "button1:" 10 45)
;;             (q/text "button2:" 10 70)
;;             )
;;     :middleware [m/pause-on-error]))

;; ;; (add-button)


;; (defn add-slider []
;;   (q/defsketch slider
;;     :title "slider"
;;     :size [200 200]
;;     :setup  (fn []
;;               (let [cp (ControlP5. (quil.applet/current-applet))
;;                     _ (-> cp
;;                           (.addSlider "bgcolor")
;;                           (.setPosition 20 50)
;;                           (.setRange 0 255))
;;                     _ (-> cp
;;                           (.addSlider "slider1")
;;                           (.setSize 20 80)
;;                           (.setPosition 0 100))]
;;                 (q/frame-rate 30)
;;                 (q/color-mode :rgb)
;;                 (q/background 255))
;;               {})
;;     :draw (fn []
;;             (q/fill 0 0 0)
;;             (q/text-size 25)
;;             )
;;     :middleware [m/pause-on-error]))

;; ;; (add-slider)

;; (defn add-menu []
;;   (q/defsketch menu
;;     :title "menu"
;;     :size [500 300]
;;     :setup (fn []
;;              (let [cp (ControlP5. (quil.applet/current-applet))
;;                    dl (.addScrollableList cp "mydropdownlist")
;;                    _ (-> dl
;;                          (.setPosition 100 100)
;;                          (.setSize 120 120)
;;                          (.setBarHeight 20)
;;                          (.setItemHeight 20)
;;                          (.addItems
;;                           '("apple" "orange" "grape" "strawberry")))]
;;                (q/frame-rate 30)
;;                (q/color-mode :rgb))
;;              {})
;;     :draw (fn [] (q/background 100))
;;     :middleware [m/pause-on-error]))

;; ;; (add-menu)
