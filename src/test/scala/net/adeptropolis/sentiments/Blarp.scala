package net.adeptropolis.sentiments

import java.io.File

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import org.apache.spark.rdd.RDD
import org.json4s.jackson.Json4sScalaModule

object Blarp {

  def apply(docs: RDD[Answer]) = {

    val results = new SentenceSentimentAnnotator[Answer].annotate(docs)

    val r = results.map(result => {
      val totalSentiment = result.sentiments.map(sentiment => mapCoreNLPSentiment(sentiment)).sum
      val sentences = result.sentiments.map(sentiment => SentenceResult(mapIntSentiment(mapCoreNLPSentiment(sentiment)), sentiment.getSentenceText))
      Result(result.doc.id, mapIntSentiment(totalSentiment), mapIntSentiment(result.doc.sentiment), result.doc.text, sentences)
    }).collect()


    val outfile = new File("/home/florian/answers.json")
    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.writeValue(outfile, r)

    val correct = r.map(x => if ((x.sentiment == "negative" && x.refSentiment == "negative") || (x.refSentiment == "positive" && x.sentiment == "positive")) 1 else 0).sum.doubleValue() / r.size
    val nearMiss = r.map(x => if ((x.sentiment == "neutral" && x.refSentiment == "negative") || (x.sentiment == "neutral" && x.refSentiment == "positive") || (x.sentiment == "negative" && x.refSentiment == "neutral") || (x.sentiment == "positive" && x.refSentiment == "neutral")) 1 else 0).sum.doubleValue() / r.size
    val realErrors = r.map(x => if ((x.sentiment == "positive" && x.refSentiment == "negative") || (x.sentiment == "negative" && x.refSentiment == "positive")) 1 else 0).sum.doubleValue() / r.size
    println(s"Correct: ${correct}, Near misses: ${nearMiss}, Full errors: ${realErrors}, total: ${correct + nearMiss + realErrors}")

    //    mapper.writeValueAsString(r)
  }

  private def mapCoreNLPSentiment(sentiment: SentenceAnnotation) = {
    sentiment.getSentiment.toLowerCase() match {
      case "negative" => -1
      case "positive" => 1
      case "neutral" => 0
      case "very negative" => -2
      case "very positive" => 2
      case _ => throw new RuntimeException(s"Unknown sentiment [${sentiment.getSentiment.toLowerCase()}]")
    }
  }

  private def mapIntSentiment(sentiment: Int) = if (sentiment < 0) {
    "negative"
  } else if (sentiment == 0) {
    "neutral"
  } else if (sentiment > 0) {
    "positive"
  } else {
    throw new RuntimeException("WTF?!")
  }


}
