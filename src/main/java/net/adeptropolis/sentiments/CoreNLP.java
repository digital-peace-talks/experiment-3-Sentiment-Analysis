package net.adeptropolis.sentiments;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.util.Iterator;
import java.util.Properties;

public class CoreNLP {

  private static final StanfordCoreNLP pipeline = getPipeline();
  private static final String LOCAL_THREADS = "36";

  public static Iterator<SentenceAnnotation> annotateSentenceSentiments(String text) {
    return pipeline.process(text)
            .get(CoreAnnotations.SentencesAnnotation.class)
            .stream()
            .map(CoreNLP::getString)
            .iterator();
  }

  private static SentenceAnnotation getString(CoreMap sentence) {
    return new SentenceAnnotation(
            sentence.get(CoreAnnotations.TextAnnotation.class),
            sentence.get(SentimentCoreAnnotations.SentimentClass.class)
    );
  }

  private static StanfordCoreNLP getPipeline() {
    Properties config = new Properties();
    config.put("annotators", "tokenize, ssplit, pos, parse, sentiment");
    config.put("threads", LOCAL_THREADS);
    return new StanfordCoreNLP(config);
  }


}
