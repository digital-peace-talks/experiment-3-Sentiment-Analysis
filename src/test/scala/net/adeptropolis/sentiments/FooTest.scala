package net.adeptropolis.sentiments

import org.apache.commons.lang3.StringEscapeUtils
import org.apache.spark.sql.Encoders
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.DataTypes


class FooTest extends SparkTestBase("Foo") with Serializable {

  private def unescapeHtml= udf((text: String) => StringEscapeUtils.UNESCAPE_HTML4.translate(text))
  private def mapSentiment= udf((text: String) => text match {
    case "neutral" => 0
    case "positive" => 1
    case "negative" => -1
    case _ => -1000
  })

  test("foo") {

//    val tweets = getTwitterData()
//    println(Blarp(tweets))
//    val answers = getAnswerData()
//    println(Blarp(answers))

    val data = getTaggedData()
    println(Blarp(data))


  }

  private def getTaggedData() = {
    session.sparkContext
      .textFile("/home/florian/tmp/labeled_corpus.csv")
      .map(line => line.split("\t").take(3))
      .filter(cols => cols.size == 3 && cols(0) != "Group" && cols(0).size > 0 && cols(0).toInt > 0 && cols(1).size > 0 && cols(2).size > 0 )
      .map(a => new Answer(a(0), a(2), a(1) match {
        case "neutral" => 0
        case "positive" => 1
        case "negative" => -1
        case _ => -1000
      }))
  }


  private def getAnswerData() = {
    session.sparkContext
      .textFile("/home/florian/Downloads/1 question - (almost) 500 answers - answers(simple).csv")
      .map(a => new Answer(a.hashCode.abs.toString, a, 0))
  }


  private def getTwitterData() = {
    val encoder = Encoders.product[Tweet]
    session.read
      .option("header", true)
      .csv("/home/florian/Datasets/Workbench/twitter-airline-sentimensts/Tweets.csv")
      .select(
        col("tweet_id").as("id"),
        col("airline_sentiment").as("sentiment_text"),
        col("airline_sentiment_confidence").cast(DataTypes.DoubleType).as("confidence"),
        unescapeHtml(col("text")).as("text"))
      .filter(col("airline_sentiment").isNotNull)
      .filter(col("airline_sentiment_confidence").isNotNull)
      .withColumn("sentiment", mapSentiment(col("sentiment_text")))
      .drop("sentiment_text")
      .filter(col("sentiment").geq(-1))
      .filter(col("confidence").geq(0.7))
      .filter(col("text").isNotNull)
      .drop("confidence")
      .as[Tweet](encoder)
      .rdd
  }


}
