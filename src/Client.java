public class Client {

    private long[] key;
    private String userID;
    private String password;

    public Client(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    public long[] getKey() {
        return key;
    }

    public void setKey(long[] key) {
        this.key = key;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
