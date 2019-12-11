(completed) Experiment: Sentiment Analysis with CoreNLP RNTN as web service
==============

This is a thin wrapper around CoreNLP's RNTN-based sentiment analyzer that
provides a proper rest API for this feature.

Like with most of CoreNLP's processing stages, any text will be segmented
into individual sentences before being analyzed. In order to provide a 
combined score for the full text snippet that has been provided, a
weighted sum  (by text length) of all sentence sentiments is returned together with the results.

Upon building the jar, all pre-trained language and sentiment models will be
automatically downloaded as built-in resources.

Build using the included Gradle wrapper
------
~~~console
./gradlew clean b
~~~

Run
------
~~~bash
java -jar -Dserver.port=6868 build/libs/sentiments-0.1.jar
~~~
The port is optional. 6868 is the default.


Example usage
-------------

Curl:
```console
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"text": "Dig it. Climate change is awesome."}' \
  http://localhost:6868/sentiments
```

HTTPie:
```console
$ http :6868/sentiments text="Dig it. Climate change is awesome."
```

Sample Response
----------------

```console
HTTP/1.1 200 OK
Content-Type: application/json;charset=utf-8
Date: Tue, 12 Mar 2019 01:18:40 GMT
Transfer-Encoding: chunked

{
    "sentences": [
        {
            "sentenceText": "Dig it.",
            "sentiment": 0
        },
        {
            "sentenceText": "Climate change is awesome.",
            "sentiment": 1
        }
    ],
    "total": 0.6666666666666666
}
```

Sentiment mappings
-------------------
All detected sentiments are mapped to a score between `-2` and `2`. More precisely:

- `-2`: Very negative
- `-1`: Negative
- `0`: Neutral
- `1`: Positive
- `2`: Very positive
