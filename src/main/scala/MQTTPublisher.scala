import com.typesafe.config.ConfigFactory
import org.eclipse.paho.client.mqttv3.{MqttClient, MqttException, MqttMessage}
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

import scala.sys.process._

/**
  *
  * MQTT publisher
  *
  * @author Shivansh
  * @mail shiv4nsh@gmail.com
  *
  */

object MQTTPublisher extends App {

  val config = ConfigFactory.load()
  val url = config.getString("mosquitto-server.url")
  val port = config.getInt("mosquitto-server.port")

  def publishToserver() = {
    println("Hey I am publishing")
    val brokerUrl = s"tcp://$url:$port"
    val topic = "TemperatureEvent"
    val tempCommand = "/opt/vc/bin/vcgencmd measure_temp"
    def getMessage = s"Temperature of CPU at ${System.currentTimeMillis()} is ${tempCommand.!!.split("=")(1)} "
    var client: MqttClient = null
    val persistence = new MemoryPersistence
    try {
      client = new MqttClient(brokerUrl, MqttClient.generateClientId, persistence)
      client.connect()
      val msgTopic = client.getTopic(topic)
      val message = new MqttMessage(getMessage.getBytes("utf-8"))
      while (true) {
        msgTopic.publish(message)
        println(s"Publishing the data topic ${msgTopic.getName} message: ${message.toString}")
        Thread.sleep(1000)
      }
    }
    catch {
      case exception: MqttException => println(s"ExceptionOccured:$exception ")
    }
    finally {
      client.disconnect()
    }
  }

  publishToserver
}
