import java.io.*;

public class ClientView {

    private ClientController controller;
    private Console console;

    public ClientView(String userID, String password) throws IOException {
        console = System.console();
        controller = new ClientController(new Client(userID, password));
        controller.connect();
    }

    public void run() throws InterruptedException {

        try {
            if (controller.login()) {
                return;
            }
        } catch (IOException e) {
            System.err.println("Unable to login");
            return;
        } catch (InterruptedException e) {
        }

        String line = "";
        while (!line.equals("finished")) {
            line = "";
            while (line.isEmpty()) {
                line = console.readLine("$ ");
                //controller.requestFile(line);
            }
        }

        try {
            controller.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Console c = System.console();
        String userID = c.readLine("Username: ");
        String password = new String(c.readPassword("Password: "));

        try {
            ClientView view = new ClientView(userID, password);
            view.run();
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Server unavailable");
            System.exit(1);
        } catch (InterruptedException e) {
        }
    }
}
