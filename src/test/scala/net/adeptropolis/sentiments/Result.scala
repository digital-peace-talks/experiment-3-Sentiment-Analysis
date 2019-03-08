package net.adeptropolis.sentiments

import com.fasterxml.jackson.annotation.JsonProperty

case class Result(@JsonProperty("id") id: String,
                  @JsonProperty("sentiment") sentiment: String,
                  @JsonProperty("refSentiment") refSentiment: String,
                  @JsonProperty("text") text: String,
                  @JsonProperty("sentences") sentences: Array[SentenceResult])