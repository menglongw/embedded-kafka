package ml.embedded.zookeeper

trait TestBase {
  private val ZOOKEEPER_CONFIG = EmbeddedZooKeeperConfig.getDefaultConfig()
  val ZOOKEEPER_CONNECT_STRING = ZOOKEEPER_CONFIG.host+":"+ZOOKEEPER_CONFIG.port
  val ZOOKEEPER_SESSION_TIMEOUT = 1000
}
