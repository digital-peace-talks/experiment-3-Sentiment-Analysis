package net.adeptropolis.sentiments

import com.fasterxml.jackson.annotation.JsonProperty

case class SentenceResult(@JsonProperty("sentiment") sentiment: String,
                          @JsonProperty("text") text: String)
