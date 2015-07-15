package helloWorld;

import util.Config;
import util.GenString;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv)	throws java.io.IOException {
    	
    	int numMsg=Config.getNumMsg();
    	int numByte=Config.getNumByte();
    	
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		
		String message =GenString.gen(numByte);
		

		long start = System.nanoTime();
	    // do stuff

		for (int i=0; i<numMsg; i++)
			channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
	    
		long end = System.nanoTime();
		long microseconds = (end - start) / 1000;
	    
		System.out.println(" [x] Total time used for "+numMsg+" messages of "+numByte+" bytes: " + microseconds + " micro seconds");
		System.out.println(" [x] Time used per message of "+numByte+" bytes: " + (float)microseconds/(numMsg*1000) + " milli seconds");
		System.out.println(" [x] Sent '" + message + "'");
		
		channel.close();
		connection.close();
    }
    //...
}