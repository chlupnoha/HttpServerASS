package server;


import util.FileUtil;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * 
 * Guided by:
 * http://javarevisited.blogspot.com/2015/06/how-to-create-http-server-in-java-serversocket-example.html#ixzz49b6d5WqE
 *
 * @author chlupnoha
 */
public class SimpleHTTPServer {

    public static final String WWW_DIR = "www";
    public static final int POOL_SIZE = 100;
    private static ServerSocket server;

    public static void main(String args[])  {
        try {
            server = new ServerSocket(8080);
            Logger.getLogger(SimpleHTTPServer.class.getName()).log(Level.INFO, "Listening for connection on port 8080 ....");
        } catch (IOException ex) {
            Logger.getLogger(SimpleHTTPServer.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        //if not exist
        FileUtil.createFolder(WWW_DIR);

        //Create Thread poll
        for (int i = 0; i < POOL_SIZE; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Socket socket = server.accept();
                            SocketHandler.handleSocket(socket);
                        } catch (IOException ex) {
                            Logger.getLogger(SimpleHTTPServer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            };
            thread.start();
            Logger.getLogger(SimpleHTTPServer.class.getName()).log(Level.INFO, "Create new thread; {0}", thread.getName());
        }
    }
    
    public static void stopServer() throws IOException{
        server.close();
    }
}
