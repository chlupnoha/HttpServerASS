package handlers;


import server.HttpExchanger;

/**
 * Interface for all Handlers
 * @author chlupnoha
 */
public interface Handler {
    
    public void handle(HttpExchanger httpRequest);
    
}
