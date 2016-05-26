package server;


import java.net.Socket;



/**
 * Transfer object for Handlers
 * @author chlupnoha
 */
public class HttpExchanger {

    private final Socket socket;
    private final SimpleRequestParser simpleRequestParser;

    public HttpExchanger(Socket socket, SimpleRequestParser simpleRequestParser) {
        this.socket = socket;
        this.simpleRequestParser = simpleRequestParser;
    }

    public Socket getSocket() {
        return socket;
    }

    public SimpleRequestParser getSimpleRequestParser() {
        return simpleRequestParser;
    }    
    
}
