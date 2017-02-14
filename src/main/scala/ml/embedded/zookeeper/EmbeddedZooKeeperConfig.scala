package ml.embedded.zookeeper

import java.io.File
import scala.reflect.io.Directory

class EmbeddedZooKeeperConfig {
  var host: String = ""
  var port: Int = 0
  var snapDir: Directory = null
  var logDir: Directory = null
  var tickTime: Int = 0
  var maxClientConnections: Int = 0
  var sessionTimeoutMs: Int = 0
  var connectionTimeoutMs: Int = 0
  var securityEnabled: Boolean = false
}

object EmbeddedZooKeeperConfig {

  def getDefaultConfig(): EmbeddedZooKeeperConfig = {
    val config = new EmbeddedZooKeeperConfig()
    config.host = "0.0.0.0"
    config.port = 2182
    config.snapDir = Directory(new File("zooKeeperSnap"))
    config.logDir = Directory(new File("zooKeeperLog"))
    config.tickTime = 1000
    config.maxClientConnections = 1024
    config.sessionTimeoutMs = 3000
    config.connectionTimeoutMs = 3000
    config.securityEnabled = false
    config
  }
}