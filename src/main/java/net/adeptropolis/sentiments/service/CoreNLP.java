package net.adeptropolis.sentiments.service;

import com.google.common.collect.ImmutableMap;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.util.Properties;
import java.util.stream.Stream;

public class CoreNLP {

  private static final StanfordCoreNLP pipeline = getPipeline();
  private static final String LOCAL_THREADS = "8";

  private static final ImmutableMap<String, Integer> scalars = ImmutableMap.of(
          "very negative", -2,
          "negative", -1,
          "neutral", 0,
          "positive", 1,
          "very positive", 2
  );

  public static Stream<SentenceAnnotation> annotateSentenceSentiments(String text) {
    return pipeline.process(text)
            .get(CoreAnnotations.SentencesAnnotation.class)
            .stream()
            .map(CoreNLP::getString);
  }

  private static SentenceAnnotation getString(CoreMap sentence) {
    return new SentenceAnnotation(
            sentence.get(CoreAnnotations.TextAnnotation.class),
            scalars.get(sentence.get(SentimentCoreAnnotations.SentimentClass.class).toLowerCase())
    );
  }

  private static StanfordCoreNLP getPipeline() {
    Properties config = new Properties();
    config.put("annotators", "tokenize, ssplit, pos, parse, sentiment");
    config.put("threads", LOCAL_THREADS);
    return new StanfordCoreNLP(config);
  }


}
