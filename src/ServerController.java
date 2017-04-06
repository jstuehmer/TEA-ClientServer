import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class ServerController extends Controller implements Runnable {

    private Server server;

    public ServerController(Server server, Socket socket) {
        super();
        this.server = server;
        setSocket(socket);
    }

    public void run() {
    }
}
