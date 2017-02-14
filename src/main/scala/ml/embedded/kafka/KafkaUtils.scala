package ml.embedded.kafka

import java.util.Properties
import kafka.admin.AdminUtils
import kafka.utils.ZkUtils
import ml.embedded.zookeeper.EmbeddedZooKeeperConfig

trait KafkaUtils {

  def createCustomTopic(topic: String, topicConfig: Map[String, String] = Map.empty,
                        partitions: Int = 1, replicationFactor: Int = 1)
                       (zooKeeperConfig: EmbeddedZooKeeperConfig): Unit = {

    val zkUtils = ZkUtils(zooKeeperConfig.host+":"+zooKeeperConfig.port, zooKeeperConfig.sessionTimeoutMs,
      zooKeeperConfig.connectionTimeoutMs, zooKeeperConfig.securityEnabled)
    val topicProperties = topicConfig.foldLeft(new Properties()) {
      case (props, (k, v)) => props.put(k, v); props
    }

    try {
      AdminUtils.createTopic(zkUtils, topic, partitions, replicationFactor, topicProperties)
    } finally {
      zkUtils.close()
    }
  }
}

object KafkaUtils extends KafkaUtils
