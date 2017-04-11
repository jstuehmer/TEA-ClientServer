import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Controller {

    protected Client client;
    protected DataInputStream in;
    protected DataOutputStream out;
    protected Socket socket;
    protected int[] key = {0x43215678, 0x12341234, 0x5678568, 0x87651234};

    public Controller() { }

    public Controller(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    protected void setClient(Client client) {
        this.client = client;
    }

    protected Socket getSocket() {
        return socket;
    }

    protected void setSocket(Socket socket) {
        this.socket = socket;
    }

    protected ByteBuffer encrypt(ByteBuffer buf) {
        return Feistel.encryptData(buf, key);
    }

    protected ByteBuffer decrypt(ByteBuffer buf) {
        return Feistel.decryptData(buf, key);
    }

    protected void send(ByteBuffer buf) throws IOException {
        out.write(buf.array());
    }

    protected void sendWithSize(ByteBuffer buf) throws IOException {
        // TODO
        ByteBuffer size = ByteBuffer.allocate(Integer.BYTES);
        buf = encrypt(buf);
        size.putInt(buf.limit() / Integer.BYTES);
        send(encrypt(size));
        send(buf);
    }

    protected ByteBuffer receive(int size) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(size);
        in.readFully(buf.array());
        return buf;
    }
}
