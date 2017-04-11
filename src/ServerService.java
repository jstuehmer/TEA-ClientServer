import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerService {

    private static String USAGE = "<number of threads>";


    private ExecutorService service;
    private ServerSocket serverSocket;
    private Server server;

    public ServerService(Server server, int threads) throws IOException {
        this.server = server;
        service = Executors.newFixedThreadPool(threads);
        serverSocket = new ServerSocket(Server.PORT);
    }

    public void run() {
        while (true) {
            try {
                service.execute(new ServerController(server, serverSocket.accept()));
            } catch (IOException e) {
                service.shutdown();
            }
        }
    }

    public static void main(String[] args) {
        if(args.length != 1) {
            System.err.println(USAGE);
            System.exit(1);
        }
        int threads = 0;
        try {
            threads = Integer.parseInt(args[0]);
        }
        catch(NumberFormatException e) {
            System.err.println(USAGE);
            System.exit(1);
        }
        try {
            ServerService serverService = new ServerService(new Server(), threads);
            serverService.run();
        } catch (IOException e) {
            System.err.println("Could not open server file");
        }
    }
}
