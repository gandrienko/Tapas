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
    UI ui=new UI();
    if (args.length==1)
      ui.setPathToData(args[0]);
  }
}
