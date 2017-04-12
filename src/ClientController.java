import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.file.*;
import java.nio.channels.SocketChannel;
import java.net.InetSocketAddress;

public class ClientController extends Controller {

    private Socket socket;

    public ClientController(Client client) {
        super(client);
    }

    public void startConnection(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    public boolean login() throws IOException {

        ByteBuffer userID1 = ByteBuffer.wrap(getClient().getUserID().getBytes());
        send(encrypt(userID1));
        ByteBuffer password1 = ByteBuffer.wrap(getClient().getPassword().getBytes());
        send(encrypt(password1));

        ByteBuffer response = decrypt(receive(6 * Integer.BYTES));
        if (!new String(response.array()).equals(Server.ACCESS_GRANTED)) {
            System.err.println("Could not log in. Credentials were wrong.");
            return false;
        }
        return true;
    }

    public void requestFile(String request) {

        ByteBuffer fileName = ByteBuffer.wrap(request.getBytes());
        try {
            sendWithSize(fileName);
            ByteBuffer receiving = receive(2 * Integer.BYTES);
            receiving = decrypt(receiving);
            if (!new String(receiving.array()).equals(Server.ACK)) {
                if (request.equals("finished"))
                    return;
                receiving = receive(2 * Integer.BYTES);
                receiving = decrypt(receiving);
                receiving = receive(Integer.BYTES * receiving.getInt());
                receiving = decrypt(receiving);
                Files.write(Paths.get(new File(request).getName()), receiving.array());
            } else {
                System.err.println("File not found on server");
            }
        } catch (IOException e) {
            System.err.println("Unable to connect to server");
            System.exit(1);
        }
    }
}
