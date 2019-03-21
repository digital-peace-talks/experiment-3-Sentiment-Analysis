package net.adeptropolis.sentiments.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.adeptropolis.sentiments.service.SentenceAnnotation;

import java.util.List;

public class SentimentAnalysisResult {

  private final double totalSentiment;
  private final List<SentenceAnnotation> sentenceAnnotations;

  public SentimentAnalysisResult(@JsonProperty("total") double totalSentiment,
                                 @JsonProperty("sentences") List<SentenceAnnotation> sentenceAnnotations) {
    this.totalSentiment = totalSentiment;
    this.sentenceAnnotations = sentenceAnnotations;
  }

  public List<SentenceAnnotation> getSentenceAnnotations() {
    return sentenceAnnotations;
  }

  public double getTotalSentiment() {
    return totalSentiment;
  }
}
