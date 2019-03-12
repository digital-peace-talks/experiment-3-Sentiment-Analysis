package net.adeptropolis.sentiments;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SentenceAnnotation implements Serializable {

  private final String sentenceText;
  private final Integer sentiment;

  public SentenceAnnotation(@JsonProperty String sentenceText, @JsonProperty Integer sentiment) {
    this.sentenceText = sentenceText;
    this.sentiment = sentiment;
  }

  public String getSentenceText() {
    return sentenceText;
  }

  public Integer getSentiment() {
    return sentiment;
  }

  @Override
  public String toString() {
    return String.format("{(%s): %s}", sentiment, sentenceText);
  }
}
