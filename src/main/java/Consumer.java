import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Consumer {

  public void listen(String queue) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(HUP.host);
    factory.setPort(5672);
    factory.setVirtualHost("tapas-vhost");
    factory.setUsername(HUP.username);
    factory.setPassword(HUP.password);

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    channel.queueDeclare(queue, false, false, false, null);

    channel.basicConsume(queue, true, (consumerTag, message) -> {
      String msg = new String(message.getBody(), "UTF-8");
      // demo.processMessage(msg);
    }, consumerTag -> {});
  }
}
