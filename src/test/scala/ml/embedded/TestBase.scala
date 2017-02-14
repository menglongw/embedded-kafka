package ml.embedded

import ml.embedded.kafka.{EmbeddedKafkaConfig, KafkaUtils}
import ml.embedded.zookeeper.EmbeddedZooKeeperConfig
import org.apache.kafka.clients.producer.ProducerRecord
import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer

trait TestBase {

  protected val zkConfig = EmbeddedZooKeeperConfig.getDefaultConfig()
  protected val kafkaConfig = EmbeddedKafkaConfig.getDefaultConfig()

  protected def toKafka(topic: String, records: collection.Seq[(String, String)]): Unit = {
    val producer = KafkaUtils.createStringSimpleProducer(kafkaConfig.connectString)

    try {
      records.foreach( x => {
        val record = new ProducerRecord[String, String](topic, x._1, x._2)
        producer.send(record).get()})
    } finally {
      producer.close()
    }
  }

  protected def pullToAssert(topic: String, timeout: Long, expected: collection.Seq[(String, String)]): Unit = {
    val consumer = KafkaUtils.createStringSimpleConsumer(kafkaConfig.connectString)
    val actual = new ArrayBuffer[(String, String)]()

    try {
      consumer.subscribe(seqAsJavaListConverter(Seq(topic)).asJava)
      var records = consumer.poll(timeout)

      while(!records.isEmpty()) {
        val it = records.iterator()

        while(it.hasNext) {
          val record = it.next()
          actual += ((record.key(), record.value()))
        }

        records = consumer.poll(timeout)
      }

      consumer.commitSync()
    } finally {
      consumer.close()
    }

    assert(expected.sameElements(actual))
  }
}
