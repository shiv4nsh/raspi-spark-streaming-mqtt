name := "spark-streaming-mqtt-raspberryPi"


val spark = "org.apache.spark" %% "spark-core" % "1.6.2"
val sparkStreamingMqtt = "org.apache.spark" %% "spark-streaming-mqtt" % "1.6.2"
val sparkStreaming = "org.apache.spark" %% "spark-streaming" % "1.6.2"


resolvers += "bintray-spark-packages" at "https://dl.bintray.com/spark-packages/maven/"

lazy val commonSettings = Seq(
  organization := "com.knoldus",
  version := "0.1.0",
  scalaVersion := "2.11.8",
  resolvers += Resolver.mavenLocal
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies ++= Seq(spark, sparkStreaming,sparkStreamingMqtt)
  )

assembleArtifact in assemblyPackageScala := false // We don't need the Scala library, Spark already includes it

assemblyMergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf") => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$") => MergeStrategy.discard
  case "reference.conf" => MergeStrategy.concat
  case _ => MergeStrategy.first
}

spName := "shiv4nsh/spark-streaming-mqtt-raspberryPi"
sparkVersion := "1.6.2"

ivyScala := ivyScala.value map {
  _.copy(overrideScalaVersion = true)
}

fork in run := true