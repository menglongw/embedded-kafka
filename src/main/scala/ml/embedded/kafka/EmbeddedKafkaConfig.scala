package ml.embedded.kafka

import java.io.File
import java.util.Properties
import ml.embedded.zookeeper.EmbeddedZooKeeperConfig
import scala.reflect.io.Directory

class EmbeddedKafkaConfig {
  var host: String = ""
  var kafkaPort: Int = 0
  var zooKeeperPort: Int = 0
  var logDir: Directory = null
  val props: Properties = new Properties()
}

object EmbeddedKafkaConfig {

  def getDefaultConfig(): EmbeddedKafkaConfig = {
    val zkConfig = EmbeddedZooKeeperConfig.getDefaultConfig()
    val config = new EmbeddedKafkaConfig()
    config.host = "localhost"
    config.kafkaPort = 2183
    config.zooKeeperPort = zkConfig.port
    config.logDir = Directory(new File("kafkaLog"))
    val properties = config.props
    properties.setProperty("zookeeper.connect", zkConfig.host+":"+config.zooKeeperPort)
    properties.setProperty("broker.id", "0")
    properties.setProperty("host.name", config.host)
    properties.setProperty("advertised.host.name", config.host)
    properties.setProperty("auto.create.topics.enable", "true")
    properties.setProperty("port", config.kafkaPort.toString)
    properties.setProperty("log.dir", config.logDir.toAbsolute.path)
    properties.setProperty("log.flush.interval.messages", 1.toString)
    properties.setProperty("log.cleaner.dedupe.buffer.size", "1048577")
    config
  }
}