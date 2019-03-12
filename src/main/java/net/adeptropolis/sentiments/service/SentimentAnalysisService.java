package net.adeptropolis.sentiments.service;

import net.adeptropolis.sentiments.CoreNLP;
import net.adeptropolis.sentiments.SentenceAnnotation;
import net.adeptropolis.sentiments.api.SentimentAnalysisResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class SentimentAnalysisService {

  private static final Pattern splitPattern = Pattern.compile("[^\\p{L}]+");

  public SentimentAnalysisResult analyze(String text) {
    List<SentenceAnnotation> annotations = CoreNLP.annotateSentenceSentiments(text)
            .collect(Collectors.toList());
    double totalScore = computeTotalScore(annotations);
    return new SentimentAnalysisResult(totalScore, annotations);
  }

  private double computeTotalScore(List<SentenceAnnotation> annotations) {
    double sumLength = annotations.stream()
            .mapToDouble(this::sentenceLength)
            .sum();
    if (sumLength == 0) {
      return 0;
    } else {
      double weightedSum = annotations.stream()
              .mapToDouble(annotation -> annotation.getSentiment() * sentenceLength(annotation))
              .sum();
      return weightedSum / sumLength;
    }

  }

  private int sentenceLength(SentenceAnnotation annotation) {
    return splitPattern.split(annotation.getSentenceText()).length;
  }

}
