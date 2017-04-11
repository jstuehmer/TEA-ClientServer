public class Client {

    private String userID;
    private String password;
    private DH clientDH;

    public Client(String userID, String password) {
        this.userID = userID;
        this.password = password;
        clientDH = new DH();
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
