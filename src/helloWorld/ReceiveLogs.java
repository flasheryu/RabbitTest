package helloWorld;

import util.Config;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class ReceiveLogs {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws java.io.IOException, java.lang.InterruptedException {
    	
    	int numMsg=Config.getNumMsg();
    	int numByte=Config.getNumByte();
	  	QueueingConsumer.Delivery delivery = null ;
        
    	ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = "speedTest";//channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

//		long start = System.nanoTime();
//		long start = System.currentTimeMillis();
//		System.out.println(" [x] start '" + start + "'");

        int count=0;
        long start=0;
		// do stuff
		
//		for (int i=0; i<numMsg; i++) {
//			delivery = consumer.nextDelivery();
//		}
		do{
			delivery=null;
			delivery = consumer.nextDelivery();
			if(count==0)
			{	start = System.currentTimeMillis();
				System.out.println(" [x] start '" + start + "'");
			}
			Thread.sleep(50);
//			if(count%1000==0){
				System.out.println(" [x] SLEEPING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//				System.out.println(" [x] SLEEPING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//				System.out.println(" [x] SLEEPING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//				System.out.println(" [x] SLEEPING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//				System.out.println(" [x] SLEEPING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//				 Thread.sleep(3000);
//			}
			String message = new String(delivery.getBody());
			System.out.println(" [x] Received '" + message + "'");
			long end = System.currentTimeMillis();
			System.out.println(" [x] end '" + end + "'");
			System.out.println(" [x] end-start '" + (float)(end-start)/numMsg + "'");
			System.out.println(" [x] end-start absolute '" + (float)(end-start) + "'");
			count++;
			System.out.println(" [x] count num is '" + count + "'");
		}		while(delivery!=null);

		
		long end = System.nanoTime();
		long microseconds = (end - start) / 1000;
		
		System.out.println(" [x] Total time receive used for "+numMsg+" messages of "+numByte+" bytes: " + microseconds + " micro seconds");
		System.out.println(" [x] Time used receive per message of "+numByte+" bytes: " + (float)microseconds/numMsg + " milli seconds");
//		String message = new String(delivery.getBody());
//		System.out.println(" [x] Received '" + message + "'");
		channel.close();
		connection.close();
		return;
		
    }
}