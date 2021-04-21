import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {
  public static String host = "83.212.170.63";
  public static String username = "tapas";
  public static String password = "2a55f70a841f18b97c3a7db939b7adc9e34a0f1b";

  public void sendMessage(String queue, String message) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(host);
    factory.setPort(5672);
    factory.setVirtualHost("tapas-vhost");
    factory.setUsername(username);
    factory.setPassword(password);

    try(Connection connection = factory.newConnection()){
      Channel channel = connection.createChannel();
      channel.queueDeclare(queue, false, false, false, null);
      channel.basicPublish("", queue, false, null, message.getBytes());
      System.out.println("message " + message + " sent");
    }
  }
}
