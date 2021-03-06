//http://stackoverflow.com/questions/8352980/application-to-application-communication-through-web-sockets
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.websocket.*;
import org.eclipse.jetty.util.ssl.SslContextFactory;

public class MyWebSocketClient{
 MyWebSocketClient()throws Exception{
  WebSocketClientFactory factory=new WebSocketClientFactory();
  try{
   factory.start();
  }catch(Exception e){e.printStackTrace();}
  //  Configuration config = new Configuration();
  WebSocketClient client //= factory.newWebSocketClient();
      //  = new WebSocketClient(new SslContextFactory(config.unsafeSsl()));
  = new WebSocketClient(new SslContextFactory(true));

      
  WebSocket.Connection connection = client.open(new URI("ws://ws.blockchain.info/inv"),new WebSocket.OnTextMessage(){
   public void onOpen(Connection connection){System.out.println("open?");}
   public void onClose(int closeCode, String message){}
   public void onMessage(String data){}
  }).get(50, TimeUnit.SECONDS);
 }

 public static void main(String[] a) throws Exception{
  new MyWebSocketClient();
 }
}