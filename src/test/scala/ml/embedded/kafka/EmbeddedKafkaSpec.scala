package ml.embedded.kafka

import ml.embedded.TestBase
import ml.embedded.zookeeper.{EmbeddedZooKeeper, EmbeddedZooKeeperConfig}
import org.scalatest.FlatSpec

class EmbeddedKafkaSpec extends FlatSpec with TestBase {

  "Messages" should "be pushed into the topic and then consumed later" in {
    val zkConfig = EmbeddedZooKeeperConfig.getDefaultConfig()
    val zkServer = new EmbeddedZooKeeper(zkConfig)
    val kafkaConfig = EmbeddedKafkaConfig.getDefaultConfig()
    val kafkaBroker = new EmbeddedKafka(kafkaConfig, zkServer)
    kafkaBroker.start()

    val topic = "test-topic"
    val records = Seq(("1", "a"), ("2", "b"))

    try{
      KafkaUtils.createCustomTopic(topic = topic, zooKeeperConfig = zkConfig)
      toKafka(topic, records)
      pullToAssert(topic, 1000, records)
    } finally {
      kafkaBroker.stop()
    }
  }
}
