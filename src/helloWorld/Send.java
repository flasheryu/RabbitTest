package helloWorld;
//import java.util.Arrays;

import util.Config;
import util.GenString;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Send {
	
  private final static String QUEUE_NAME = "speedTest";

  public static void main(String[] argv) throws Exception {
      	      
	  int numMsg=Config.getNumMsg();
	  int numByte=Config.getNumByte();
	  ConnectionFactory factory = new ConnectionFactory();
	  factory.setHost("localhost");
	  Connection connection = factory.newConnection();
	  Channel channel = connection.createChannel();

	  channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//    String message = "Hello World!enheng?";
	  String message =GenString.gen(numByte);

	  long start = System.nanoTime();
    // do stuff

	  for (int i=0; i<numMsg; i++)
		  channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
    
	  long end = System.nanoTime();
	  long microseconds = (end - start) / 1000;
    
	  System.out.println(" [x] Total time used for "+numMsg+" messages of "+numByte+" bytes: " + microseconds + " micro seconds");
	  System.out.println(" [x] Time used per message of "+numByte+" bytes: " + (float)microseconds/numMsg + " milli seconds");
	  System.out.println(" [x] Sent '" + message.toString() + "'");

	  channel.close();
	  connection.close();
  }
}