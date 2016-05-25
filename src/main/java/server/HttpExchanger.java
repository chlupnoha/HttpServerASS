package server;


import java.net.Socket;



/**
 *
 * @author chlupnoha
 */
public class HttpExchanger {

    private Socket socket;
    private SimpleRequestParser simpleRequestParser;

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
