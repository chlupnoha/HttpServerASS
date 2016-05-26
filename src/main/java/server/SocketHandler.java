package server;




import handlers.DeleteHandler;
import handlers.GetHandler;
import handlers.PutHandler;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author chlupnoha
 */
public class SocketHandler {

    //socket
    //server
    public static void handleSocket(Socket socket) throws IOException {

        SimpleRequestParser parser = new SimpleRequestParser(socket.getInputStream());
        HttpExchanger exchanger = new HttpExchanger(socket, parser);
        System.out.println(parser.getBody());
        
        switch (parser.getMethod()) {
            case "GET":
                new GetHandler().handle(exchanger);
                break;
            case "PUT":
                new PutHandler().handle(exchanger);
                break;
            case "DELETE":
                new DeleteHandler().handle(exchanger);
                break;
            default:
                break;
        }
    }

}
