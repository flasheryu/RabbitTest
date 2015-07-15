package helloWorld;

import util.Config;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class Recv {
	
    private final static String QUEUE_NAME = "speedTest";

    public static void main(String[] argv) throws Exception {

  	  	int numMsg=Config.getNumMsg();
  	  	int numByte=Config.getNumByte();
	  	QueueingConsumer.Delivery delivery = null ;

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(QUEUE_NAME, true, consumer);
		
		long start = System.nanoTime();
		// do stuff
		
		for (int i=0; i<numMsg; i++) {
			delivery = consumer.nextDelivery();
		}
		
		long end = System.nanoTime();
		long microseconds = (end - start) / 1000;
		
		System.out.println(" [x] Total time receive used for "+numMsg+" messages of "+numByte+" bytes: " + microseconds + " micro seconds");
		System.out.println(" [x] Time used receive per message of "+numByte+" bytes: " + (float)microseconds/numMsg + " milli seconds");
		String message = new String(delivery.getBody());
		System.out.println(" [x] Received '" + message + "'");
		channel.close();
		connection.close();

  }
}