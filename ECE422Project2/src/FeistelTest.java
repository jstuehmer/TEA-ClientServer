import java.nio.ByteBuffer;
import java.math.BigInteger;

public class FeistelTest {

    public FeistelTest() { }


    public static void main(String[] args) {

        int[] key = {0x33215671, 0x2241234, 0x5648568, 0x87151234};
        String data = "HelloWorld!";

        ByteBuffer message = ByteBuffer.wrap(data.getBytes());
        message = Feistel.encryptData(message, key);
        System.out.println(new String(message.array()));

        message = Feistel.decryptData(message, key);
        System.out.println(new String(message.array()));
    }
}
