import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class ClientController extends Controller {

    private InetSocketAddress address;
    private SocketChannel clientChannel;

    public ClientController(Client client) {
        super(client);
    }

    public void connect() throws IOException {
        address = new InetSocketAddress("localhost", 16000);
        clientChannel = SocketChannel.open(address);
    }

    public void disconnect() throws IOException {
        clientChannel.close();
    }

    public boolean login() throws IOException, InterruptedException {

        ByteBuffer userID = ByteBuffer.wrap(getClient().getUserID().getBytes());
        ByteBuffer password = ByteBuffer.wrap(getClient().getPassword().getBytes());

        send(userID, clientChannel);
        send(password, clientChannel);

        /*
        ByteBuffer buffer = ByteBuffer.allocate(256);
        clientChannel.read(buffer);

        ByteBuffer response = decrypt(buffer);
        if (!new String(response.array()).equals("access-granted")) {
            System.err.println("Could not log in. Credentials were wrong.");
            return true;
        } */
        return false;
    }
}
