import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class ServerController extends Controller implements Runnable {

    private Server server;

    private void setStreams() throws IOException {
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    private void sendAD() throws IOException {
        send(encrypt(ByteBuffer.wrap(Server.ACCESS_DENIED.getBytes())));
    }

    private void sendAG() throws IOException {
        send(encrypt(ByteBuffer.wrap(Server.ACCESS_GRANTED.getBytes())));
    }

    private void sendFNF() throws IOException {
        send(encrypt(ByteBuffer.wrap(Server.FILE_NOT_FOUND.getBytes())));
    }

    public ServerController(Server server, Socket socket) {
        super();
        this.server = server;
        setSocket(socket);
    }

    @Override
    public void run() {
        try {
            setStreams();
        } catch (IOException e) {
            System.err.println("Unable to connect with client");
            return;
        }

        try {
            ByteBuffer userID2 = decrypt(receive(4 * Integer.BYTES));

            for (String user: server.getUsers()) {
                if (user.equals(new String(userID2.array()))) {
                    ByteBuffer password2 = receive(4 * Integer.BYTES);
                    if (server.getPassword(user).equals(new String(password2.array()))) {
                        sendAG();
                        System.out.println("sendAG()");
                        //processCommands();
                        return;
                    }
                }
            }
            
            send(ByteBuffer.wrap("No".getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   

}
