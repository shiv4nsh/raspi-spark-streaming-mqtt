import com.typesafe.config.ConfigFactory
import org.apache.spark.SparkConf
import org.apache.spark.streaming.mqtt.MQTTUtils
import org.apache.spark.streaming.{Time, Seconds, StreamingContext}

import scala.util.Try

/**
  * @author Shivansh
  * @mail shiv4nsh@gmail.com
  */
object TemperatureStreaming extends App {

  val sparkConf = new SparkConf().setAppName("RaspberryPi-Temperature").setMaster("local[*]")
  val ssc = new StreamingContext(sparkConf, Seconds(5))
  val brokerUrl = Try(ConfigFactory.load().getString("brokerUrl")).toOption.fold("tcp://localhost:1883")(x => x)
  val TOPIC = "TemperatureEvent"
  val temperatureStream = MQTTUtils.createStream(ssc, brokerUrl, TOPIC)
  temperatureStream.compute(Time(5)).fold()(x => x)
  temperatureStream.foreachRDD(s => s.foreach(d => println(s"Message from Pi:$d")))
  ssc.start()
  ssc.awaitTermination()
}
