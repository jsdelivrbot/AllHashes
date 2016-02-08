; mvn org.apache.maven.plugins:maven-dependency-plugin:2.10:get -Dartifact=org.clojure:clojure:1.8.0
; java -jar ~/.m2/repository/org/clojure/clojure/1.8.0/clojure-1.8.0.jar bbc.clj
(require '[clojure.xml :as xml])

;https://www.bbc.com/news/10628494 :
;https://feeds.bbci.co.uk/news/rss.xml
; title
; description
; link
; guid
; pubDate

(defn h [b] ; https://gist.github.com/kisom/1698245
 (let [h (java.security.MessageDigest/getInstance "SHA-256")]
  (. h update b)
  (.digest h)))

(defn hh [m] (apply str (map #(format "%02x" %) m)))

(defn parsePubDate [s]
 ;http://stackoverflow.com/questions/2705548/parse-rss-pubdate-to-date-object-in-java
 (.parse
  (java.text.SimpleDateFormat. "EEE, dd MMM yyyy HH:mm:ss zzz"
   java.util.Locale/ENGLISH) s))

(defn starTime [d]
 (/ (- (.getTime d) 1443408427000) 1000))

(defn starTimeD [d]
 (format "%f" (/ (- (.getTime d) 1443408427000) 1000.0)))

(defn getTag [x n]
 (first (:content (get (:content x) n))))

(defn spitNews [s]
 (let [
   sl (slurp s)
   ss (hh (h (.getBytes sl)))
   sf (str "1220" ss ".xml")]
  (if (not (.exists (java.io.File. sf)))
   (do
    (spit "FeedFetch.txt"
     (str (starTimeD (java.util.Date.))
      " " ss " " s "\n") :append true)
    (spit sf sl)))
  ss))

(println (apply str (map
 (fn [x]
  (if (= :item (:tag x))
   (let [
     s (str
      (starTime (parsePubDate (getTag x 4))) "\n" ;pubDate
      (getTag x 3) "\n";guid
      (getTag x 0) "\n";title
      (getTag x 1)) ;description
     hs (hh (h (.getBytes s)))]
    (do
     (spit (str "1220" hs ".news") s)
     (str hs "\n")))))
 (:content (first (:content (xml/parse
  (str "1220"
   (spitNews
    "https://feeds.bbci.co.uk/news/rss.xml")
   ".xml"))))))))