package ml.embedded.kafka

import kafka.server.{KafkaConfig, KafkaServer}

abstract class EmbeddedKafkaBase(config: EmbeddedKafkaConfig) {

  protected var broker: KafkaServer = null

  def start(): KafkaServer = {
    broker = new KafkaServer(new KafkaConfig(config.props))
    broker.startup()
    broker
  }

  def stop(): Unit = {
    broker.shutdown()
    broker.awaitShutdown()
    config.logDir.deleteRecursively()
  }
}
