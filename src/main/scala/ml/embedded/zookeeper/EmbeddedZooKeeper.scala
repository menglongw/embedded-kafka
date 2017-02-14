package ml.embedded.zookeeper

import java.net.InetSocketAddress
import org.apache.zookeeper.server.{ServerCnxnFactory, ZooKeeperServer}

class EmbeddedZooKeeper(config: EmbeddedZooKeeperConfig) {

  private var factory: ServerCnxnFactory = null

  def start(): ServerCnxnFactory = {
    val zk = new ZooKeeperServer(config.snapDir.toFile.jfile, config.logDir.toFile.jfile, config.tickTime)
    factory = ServerCnxnFactory.createFactory
    factory.configure(new InetSocketAddress(config.host, config.port), config.maxClientConnections)
    factory.startup(zk)
    factory
  }

  def stop(): Unit = {
    factory.shutdown()
    config.snapDir.deleteRecursively()
    config.logDir.deleteRecursively()
  }
}
