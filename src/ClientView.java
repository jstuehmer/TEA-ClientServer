import java.io.*;

public class ClientView {

    private ClientController controller;
    private Console console;

    public ClientView(int port, String userID, String password) throws IOException {
        console = System.console();
        controller = new ClientController(new Client(userID, password));
        controller.startConnection("localhost", port);
    }

    public void run() {

        try {
            if (!controller.login()) {
                return;
            }
        } catch (IOException e) {
            System.err.println("Unable to login");
            return;
        }

        String line = "";
        while (!line.equals("finished")) {
            line = "";
            while (line.isEmpty()) {
                line = console.readLine("stumpy$ ");
                controller.requestFile(line);
            }
        }

        try {
            controller.stopConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        if (args.length < 1) {
            throw new IllegalArgumentException("ClientView expects port number");
        }
        int port = Integer.parseInt(args[0]);

        Console c = System.console();
        String userID = c.readLine("Username: ");
        String password = new String(c.readPassword("Password: "));

        try {
            ClientView view = new ClientView(port, userID, password);
            view.run();
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Server unavailable");
            System.exit(1);
        }

    }

}
