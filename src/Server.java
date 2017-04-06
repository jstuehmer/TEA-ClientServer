import java.util.HashMap;
import java.util.Set;

public class Server {

    private HashMap<String, long[]> users;
    private DH serverDH;

    public static String ACK = "ack";
    public static String ACCESS_DENIED = "access-denied";
    public static String ACCESS_GRANTED = "access-granted";
    public static String FILE_NOT_FOUND = "file-not-found";

    public Server(HashMap<String, long[]> users) {
        this.users = users;
        serverDH = new DH();
    }
/*
    public long[] getPassword(String userID) {
        return users.getOrDefault(userID, "");
    }
*/
}
