import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Consumer {
  public static String host = "83.212.170.63";
  public static String username = "tapas";
  public static String password = "2a55f70a841f18b97c3a7db939b7adc9e34a0f1b";

  public void listen(String queue) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(host);
    factory.setPort(5672);
    factory.setVirtualHost("tapas-vhost");
    factory.setUsername(username);
    factory.setPassword(password);

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    channel.queueDeclare(queue, false, false, false, null);

    channel.basicConsume(queue, true, (consumerTag, message) -> {
      String msg = new String(message.getBody(), "UTF-8");
      // demo.processMessage(msg);
    }, consumerTag -> {});
  }
}
