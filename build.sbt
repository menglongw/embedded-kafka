name := "embedded-kafka"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka_2.11" % "0.10.1.1",
  "org.apache.kafka" % "kafka-streams" % "0.10.1.1",
  "org.apache.zookeeper" % "zookeeper" % "3.4.8",
  "org.scala-lang" % "scala-reflect" % "2.11.8",
  "org.scalatest" %% "scalatest" % "3.0.1" % Test
)