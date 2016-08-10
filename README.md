# raspi-spark-streaming-mqtt
The basic purpose of this project is that the raspberryPi sends the temperature event to the server and spark streams the data using the spark streaming 

##What we'll build 
A solution where the Dev_Raspi can send events to the broker and then we can consume these events using the Spark-streaming Api

##Steps to get started:
  1.This project will use Mosquitto as a broker. So if you have not installed mosquitto please follow the links here  (https://mosquitto.org/download/)
  2. We are considering here two device solution : Dev_Laptop(for SparkStreaming ) and Dev_RasberryPi (MQTTPublisher) (Note: You can use the RaspberryPi for both the SparkStreaming and as the MQTTPublisher as well.
  3. So start your Mosquitto on Dev_Laptop.
  4. Configure the application.conf with the port and the url of the mosquitto server. 
  5. Make a assembly jar of this project using the following command.

      sbt assembly
  
  6. Copy the jar to RaspberryPi using the scp command.

         scp raspi-mqtt-client.jar pi@<pi-ip-address>:/home/pi/Projects/scala
  
  7. Run the Publisher on Dev_RaspberryPi using the following command
  
         java -cp raspi-mqtt-client.jar com.knoldus.MQTTPublisher

    This will start the publisher to sending the temperature events to broker. 
  
  8. Run the Subscriber on Dev_Laptop using the following command
  
         java -cp raspi-mqtt-client.jar com.knoldus.TemperatureStreaming

##What you accomplished: 

You have made a simple IOT solution where you made events and analyzed it using the Spark Streaming !

##Sumary 

Hurray ! You have successfully developed a sample IOT application :)
