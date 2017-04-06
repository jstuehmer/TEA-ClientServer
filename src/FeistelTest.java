import java.nio.ByteBuffer;

public class FeistelTest {

    public FeistelTest() { }

    public static void main(String[] args) {

        long[] key = {5L, 4L, 17L, 1231242141253L};
        String data = "HelloWorld";

        ByteBuffer message = ByteBuffer.wrap(data.getBytes());
        message = Feistel.encryptData(message, key);
        message = Feistel.decryptData(message, key);
        System.out.println(new String(message.array()));
    }
}
