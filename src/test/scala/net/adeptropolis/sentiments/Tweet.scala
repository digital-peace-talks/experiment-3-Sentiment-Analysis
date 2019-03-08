package net.adeptropolis.sentiments

case class Tweet(override val id: String, override val text: String, sentiment: Int) extends Document(id, text)