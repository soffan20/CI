package dd2480.group4;
import dd2480.group4.execute.Runner;
import dd2480.group4.net.Notification;
import dd2480.group4.net.RequestHandler;
import dd2480.group4.net.Http;
import org.eclipse.jetty.server.Server;

/**
 * Class for starting the server
 */
public class Main {

    /**
     * The function which actually starts the server.
     * @param args Not used
     */
    public static void main(String[] args) {
        Server server = new Server(8004);
        try {
            server.setHandler(new RequestHandler(new Runner(), new Notification(new Http())));
            server.start();
            server.join();
        } catch(Exception e) {
            System.err.println(e);
        }
    }
}
