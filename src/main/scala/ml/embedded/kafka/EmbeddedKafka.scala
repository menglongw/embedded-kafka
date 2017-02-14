package ml.embedded.kafka

import kafka.server.KafkaServer
import ml.embedded.zookeeper.EmbeddedZooKeeper

class EmbeddedKafka(config: EmbeddedKafkaConfig, zooKeeper: EmbeddedZooKeeper) extends EmbeddedKafkaBase(config) {

  override def start(): KafkaServer = {
    zooKeeper.start()
    super.start()
  }

  override def stop(): Unit = {
    super.stop()
    zooKeeper.stop()
  }
}
