import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {

  public void sendMessage(String queue, String message) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(HUP.host);
    factory.setPort(5672);
    factory.setVirtualHost("tapas-vhost");
    factory.setUsername(HUP.username);
    factory.setPassword(HUP.password);

    try(Connection connection = factory.newConnection()){
      Channel channel = connection.createChannel();
      channel.queueDeclare(queue, false, false, false, null);
      channel.basicPublish("", queue, false, null, message.getBytes());
      System.out.println("message " + message + " sent");
    }
  }
}
