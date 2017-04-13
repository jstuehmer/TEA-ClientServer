import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public class ServerController extends Controller implements Runnable {

    private Server server;
    private Selector selector;
    private ServerSocketChannel serverChannel;
    private InetSocketAddress address;

    public ServerController(Server server) {
        super();
        this.server = server;
    }

    private void listen() throws IOException {
        // Multiplexor of SelectableChannel objects
        selector = Selector.open();
        // Selectable channel for stream-oriented listening sockets
        serverChannel = ServerSocketChannel.open();
        address = new InetSocketAddress("localhost", 16000);
        // Binds channel's socket to local address
        serverChannel.bind(address);
        // Adjusts channel's blocking mode.
        serverChannel.configureBlocking(false);
    }

    @Override
    public void run() {
        try {
            listen();
        } catch (IOException e) {
            System.err.println("Unable to connect with client");
            return;
        }

        try {
            int ops = serverChannel.validOps();
            SelectionKey sKey = serverChannel.register(selector, ops, null);

            // Infinite loop... keep server running
            while (true) {

                log("Server waiting for new connection and buffer select...");
                // Selects a set of keys whose corresponding channels are ready for I/O operations
                selector.select();

                // token representing the registration of a SelectableChannel with a Selector
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> selectionIterator = selectionKeys.iterator();

                while (selectionIterator.hasNext()) {
                    SelectionKey myKey = selectionIterator.next();

                    // Tests whether this key's channel is ready to accept a new socket connection
                    if (myKey.isAcceptable()) {
                        SocketChannel clientChannel = serverChannel.accept();

                        // Adjusts this channel's blocking mode to false
                        clientChannel.configureBlocking(false);
 
                        // Operation-set bit for read operations
                        clientChannel.register(selector, SelectionKey.OP_READ);
                        log("Connection Accepted: " + clientChannel.getLocalAddress() + "\n");

                    // Tests whether this key's channel is ready for reading
                    } else if (myKey.isReadable()) {

                        SocketChannel clientChannel = (SocketChannel) myKey.channel();

                        ByteBuffer size = ByteBuffer.allocate(4);
                        clientChannel.read(size);
                        size.flip();
                        ByteBuffer userID = ByteBuffer.allocate(size.getInt());
                        clientChannel.read(userID);
                        size.compact();

                        //userID = decrypt(userID);
                        log("Message received: " + new String(userID.array()));
                        //buffer.clear();
                        //password = decrypt(password);
                        //log("Message received: " + new String(bufferArray[1].array()));
/*
                        for (String user: server.getUsers()) {
                            if (user.equals(new String(userID.array()))) {
                                if (server.getPassword(user).equals(new String(password.array()))) {
                                    //sendAG();
                                    System.out.println("sendAG()");
                                    //processCommands();
                                    return;
                                }
                            }
                        }*/

                        if (userID.equals("Justin")) {
                            clientChannel.close();
                        }
                    }
                    selectionIterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void log(String str) {
        System.out.println(str);
    }

    private void sendAD() throws IOException {
        //send(encrypt(ByteBuffer.wrap(Server.ACCESS_DENIED.getBytes())));
    }   

}
