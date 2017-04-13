import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) throws IOException {
            ServerController controller = new ServerController(new Server());
            controller.run();
    }
}
