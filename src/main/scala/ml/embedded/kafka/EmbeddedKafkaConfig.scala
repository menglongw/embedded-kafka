package ml.embedded.kafka

import java.io.File
import java.util.Properties
import ml.embedded.zookeeper.EmbeddedZooKeeperConfig
import scala.reflect.io.Directory

class EmbeddedKafkaConfig {
  var host: String = ""
  var port: Int = 0
  var logDir: Directory = null
  val props: Properties = new Properties()

  def connectString: String = host+":"+port
}

object EmbeddedKafkaConfig {

  def getDefaultConfig(): EmbeddedKafkaConfig = {
    val zkConfig = EmbeddedZooKeeperConfig.getDefaultConfig()
    val config = new EmbeddedKafkaConfig()
    config.host = "0.0.0.0"
    config.port = 9696
    config.logDir = Directory(new File("kafkaLog"))
    val properties = config.props
    properties.setProperty("zookeeper.connect", zkConfig.host+":"+zkConfig.port)
    properties.setProperty("broker.id", 0.toString)
    properties.setProperty("host.name", config.host)
    properties.setProperty("advertised.host.name", config.host)
    properties.setProperty("auto.create.topics.enable", "true")
    properties.setProperty("port", config.port.toString)
    properties.setProperty("log.dir", config.logDir.toAbsolute.path)
    properties.setProperty("log.flush.interval.messages", 1.toString)
    config
  }
}