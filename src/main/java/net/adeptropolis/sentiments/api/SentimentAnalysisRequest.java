package net.adeptropolis.sentiments.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SentimentAnalysisRequest {

  private final String text;

  public SentimentAnalysisRequest(@JsonProperty("text") String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }
}
