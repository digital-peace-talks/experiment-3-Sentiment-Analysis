package net.adeptropolis.sentiments;

import java.io.Serializable;

public class SentenceAnnotation implements Serializable {

  private final String sentenceText;
  private final String sentiment;

  public SentenceAnnotation(String sentenceText, String sentiment) {
    this.sentenceText = sentenceText;
    this.sentiment = sentiment;
  }

  public String getSentenceText() {
    return sentenceText;
  }

  public String getSentiment() {
    return sentiment;
  }

  @Override
  public String toString() {
    return String.format("{(%s): %s}", sentiment, sentenceText);
  }
}
