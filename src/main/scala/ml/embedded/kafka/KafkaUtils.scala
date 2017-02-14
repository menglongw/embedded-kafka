package ml.embedded.kafka

import java.util.Properties
import kafka.admin.AdminUtils
import kafka.utils.ZkUtils
import ml.embedded.zookeeper.EmbeddedZooKeeperConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}

trait KafkaUtils {

  def createCustomTopic(topic: String, topicProperties: Properties = new Properties(), partitions: Int = 1,
                        replicationFactor: Int = 1, zooKeeperConfig: EmbeddedZooKeeperConfig): Unit = {

    val zkUtils = ZkUtils(zooKeeperConfig.connectString, zooKeeperConfig.sessionTimeoutMs,
      zooKeeperConfig.connectionTimeoutMs, zooKeeperConfig.securityEnabled)

    try {
      AdminUtils.createTopic(zkUtils, topic, partitions, replicationFactor, topicProperties)
    } finally {
      zkUtils.close()
    }
  }

  def createStringSimpleProducer(connectString: String, clientId: String = "p1"): KafkaProducer[String, String] = {
    val props = new Properties()
    props.setProperty("bootstrap.servers", connectString)
    props.setProperty("acks", "all")
    props.setProperty("client.id", clientId)
    props.setProperty("retries", 0.toString)
    props.setProperty("key.serializer", classOf[StringSerializer].getName())
    props.setProperty("value.serializer", classOf[StringSerializer].getName())
    val producer = new KafkaProducer[String, String](props)
    producer
  }

  def createStringSimpleConsumer(connectString: String, clientId: String = "c1", groupId: String = "cg1"): KafkaConsumer[String, String] = {
    val props = new Properties()
    props.setProperty("client.id", clientId)
    props.setProperty("group.id", groupId)
    props.setProperty("bootstrap.servers", connectString)
    props.setProperty("auto.offset.reset", "earliest")
    props.setProperty("enable.auto.commit", "false")
    props.setProperty("key.deserializer", classOf[StringDeserializer].getName())
    props.setProperty("value.deserializer", classOf[StringDeserializer].getName())
    val consumer = new KafkaConsumer[String, String](props)
    consumer
  }
}

object KafkaUtils extends KafkaUtils
