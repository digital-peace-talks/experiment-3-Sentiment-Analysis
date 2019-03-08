package net.adeptropolis.sentiments

case class AnnotatedDocument[D <: Document](sentiments: Array[SentenceAnnotation], doc: D) {

  override def toString: String = s"Sentiments: ${sentiments.mkString(", ")}, document: $doc"

}