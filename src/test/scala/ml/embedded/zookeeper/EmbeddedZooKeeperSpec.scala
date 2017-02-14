package ml.embedded.zookeeper

import ml.embedded.TestBase
import org.apache.zookeeper.ZooDefs.Ids
import org.apache.zookeeper.data.Stat
import org.apache.zookeeper.{CreateMode, ZooKeeper}
import org.scalatest.{FlatSpec, Matchers}

class EmbeddedZooKeeperSpec extends FlatSpec with Matchers with TestBase {

  "Data" should "be stored to the EmbeddedZooKeeper successfully" in {

    val zkServer = new EmbeddedZooKeeper(zkConfig)
    zkServer.start()
    val data = "abc"
    val path = "/test"
    var result: Array[Byte] = null

    try {
      val zk = new ZooKeeper(zkConfig.connectString, zkConfig.sessionTimeoutMs, null)
      zk.create(path, data.getBytes, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)
      result = zk.getData(path, true, new Stat())
    } finally {
      zkServer.stop()
    }

    new String(result) should equal (data)
  }
}
