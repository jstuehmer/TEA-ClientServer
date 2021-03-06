import java.util.HashMap;
import java.util.Set;
import java.nio.ByteBuffer;

public class Server {

    private HashMap<String, String> users = new HashMap<String, String>();
    private DH serverDH;
    private int[] key = {0x43215678, 0x12341234, 0x5678568, 0x87651234};

    public static int PORT = 16000;

    public static String ACK = "ack";
    public static String ACCESS_DENIED = "access-denied";
    public static String ACCESS_GRANTED = "acces-granted";
    public static String FILE_NOT_FOUND = "file-not-found";

    public Server() {
        String p = "shit";
        ByteBuffer message = ByteBuffer.wrap(p.getBytes());
        users.put("Justin",  new String(Feistel.encryptData(message, key).array()));
        //users.put("Justin", "password");
    }

    public Set<String> getUsers() {
        return users.keySet();
    }
/*
    public Server(HashMap<String, int[]> users) {
        this.users = users;
        serverDH = new DH();
    }
*/

    public String getPassword(String userID) {
        return users.get(userID);
    }
}
