import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class Controller {

    protected Client client;
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

    protected ByteBuffer encrypt(ByteBuffer buf) {
        return Feistel.encryptData(buf, key);
    }

    protected ByteBuffer decrypt(ByteBuffer buf) {
        return Feistel.decryptData(buf, key);
    }

    protected void send(ByteBuffer buf, SocketChannel ch) throws IOException {
        ByteBuffer size = ByteBuffer.allocate(4);
        size.putInt(buf.capacity());
        size.flip();
        ch.write(size);
        size.clear();
        ch.write(buf);
        buf.clear();
    }

    protected void receive() {
    }
}
