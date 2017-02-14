package ml.embedded.zookeeper

import org.apache.zookeeper.ZooDefs.Ids
import org.apache.zookeeper.data.Stat
import org.apache.zookeeper.{CreateMode, WatchedEvent, Watcher, ZooKeeper}
import org.scalatest.{FlatSpec, Matchers}

class EmbeddedZooKeeperSpec extends FlatSpec with Matchers with TestBase {

  "Data" should "be stored to the EmbeddedZooKeeper successfully" in {

    val zkServer = new EmbeddedZooKeeper(EmbeddedZooKeeperConfig.getDefaultConfig())
    zkServer.start()

    val zk = new ZooKeeper(ZOOKEEPER_CONNECT_STRING, ZOOKEEPER_SESSION_TIMEOUT, new Watcher {
      override def process(event: WatchedEvent): Unit = {}
    })

    val data = "abc"
    val path = "/test"
    zk.create(path, data.getBytes, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)
    val result = zk.getData(path, true, new Stat())
    zkServer.stop()

    new String(result) should equal (data)
  }
}
