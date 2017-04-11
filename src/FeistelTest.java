import java.nio.ByteBuffer;
import java.math.BigInteger;

public class FeistelTest {

    public FeistelTest() { }


    public static void main(String[] args) {

        int[] key = {0x33215671, 0x2241234, 0x5648568, 0x87151234};
        String data = "HelloWorld!";

        ByteBuffer message = ByteBuffer.wrap(data.getBytes());
        String s = new String(message.array());
        System.out.println(String.format("%040x", new BigInteger(1, s.getBytes())));
        System.out.println(s);
        message = Feistel.encryptData(message, key);
        s = new String(message.array());
        System.out.println(String.format("%040x", new BigInteger(1, s.getBytes())));
        System.out.println(s);
        message = Feistel.decryptData(message, key);
        s = new String(message.array());
        System.out.println(String.format("%040x", new BigInteger(1, s.getBytes())));
        System.out.println(s);
    }
}
