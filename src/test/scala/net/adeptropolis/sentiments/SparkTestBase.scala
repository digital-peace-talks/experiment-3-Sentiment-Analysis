package net.adeptropolis.sentiments

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.junit.BeforeClass
import org.scalatest.{BeforeAndAfterAll, FunSuite}

class SparkTestBase(testName: String, scratch: String = null) extends FunSuite with BeforeAndAfterAll with Serializable {

  var session: SparkSession = null

  @BeforeClass def initialize(): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
  }

  override def beforeAll() {
    super.beforeAll()
    val conf: SparkConf = assembleSparkConf
    session = SparkSession.builder()
      .config(conf)
      .getOrCreate()
  }

  private def assembleSparkConf = {
    val conf = new SparkConf()
      .setAppName(testName)
      .setMaster("local[36]")
    //      .set("spark.eventLog.enabled", "true")
    //      .set("spark.eventLog.dir", "/tmp/spark-events")
    if (scratch != null) {
      conf.set("spark.local.dir", scratch)
    }
    conf
  }

  override def afterAll() {
    super.afterAll()
    session.stop()
  }

}