import java.awt.*;

public class Main {

  public static void main(String[] args) {

    System.out.println("Hello World!");
    Sender sender=new Sender();
    try {
      sender.sendMessage("test","greetings");
    } catch (Exception e) {
      System.out.println("exception="+e);
    }
    new UI();
  }
}
