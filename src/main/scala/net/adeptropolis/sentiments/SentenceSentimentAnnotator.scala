package net.adeptropolis.sentiments

import org.apache.spark.rdd.RDD

import scala.collection.JavaConverters._

class SentenceSentimentAnnotator[D <: Document] extends Serializable {

  def annotate(docs: RDD[D]) = docs.mapPartitions(annotatePartition)

  private def annotatePartition(docs: Iterator[D]) = {
    docs.toStream.par
      .map(annotateDocument)
      .iterator
  }

  private def annotateDocument(doc: D) = {
    val sentiments = CoreNLP.annotateSentenceSentiments(doc.text)
      .asScala
      .toArray
    AnnotatedDocument(sentiments, doc)
  }

}
