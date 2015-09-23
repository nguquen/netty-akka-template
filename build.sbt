name := "netty-akka-template"

version := "1.0"

scalaVersion := "2.11.7"

val akkaVersion = "2.3.14"

libraryDependencies ++= Seq(
  "io.netty" % "netty-all" % "4.0.31.Final",
  "com.typesafe.akka" % "akka-actor_2.11" % akkaVersion,
  "com.typesafe.akka" % "akka-slf4j_2.11" % akkaVersion,
  "com.typesafe.akka" % "akka-cluster_2.11" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.1.3"
)

mainClass in Compile := Some("com.gnt.server.MainServer")
enablePlugins(JavaServerAppPackaging)

import NativePackagerHelper._
mappings in Universal ++= directory("conf")
